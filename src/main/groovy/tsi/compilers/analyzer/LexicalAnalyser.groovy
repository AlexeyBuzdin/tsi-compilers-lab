package tsi.compilers.analyzer

import java.util.stream.Collectors

import static ApplicationConstants.DELIMITERS
import static ApplicationConstants.KEYWORDS

class LexicalAnalyser {

    def cursorPosition = 0
    def identifierCounter = 100
    def lexems = new ArrayList<ParseResult>()

    def currentPosition = 0
    def currentChars = new ArrayList<Character>()

    public List<ParseResult> parseToLexems(String string) {

        for (char c : string.chars) {
            if(!registeredSymbol(c)) {
                throw new IllegalArgumentException("" + c + " at position " + cursorPosition + " is illegal")
            }

            if (isDelimiter(c)){
                if (!partOfComplexDelimiterInBuffer(c)) {
                    pushWord()
                }
                addCharToStack(c)
            } else if (isWhitespace(c)) {
                pushWord()
                currentPosition = cursorPosition+1
            } else if (currentWordDoesNotMatchTypeWithChar(c)) {
                pushWord()
                addCharToStack(c)
            } else {
                addCharToStack(c)
            }
            cursorPosition++
        }

        clearBuffer()

        return lexems
    }

    boolean registeredSymbol(char c) {
        isDelimiter(c) || isWhitespace(c) || isAlpha(c) || isNumber(c);
    }

    static boolean isDelimiter(char c) {
        DELIMITERS.stream().filter({
            it.value.length() == 1 && it.value.charAt(0) == c
        })
        .count() == 1
    }

    static boolean isWhitespace(char c) {
        Character.isWhitespace(c as int)
    }

    static boolean isAlpha(char c) {
        Character.isAlphabetic(c as int)
    }

    static boolean isNumber(char c) {
        Character.isDigit(c as int)
    }

    static boolean isNumber(String s) {
        s.chars().filter({!isNumber(it as char)}).count() == 0
    }

    boolean currentWordIsEmpty() {
        currentChars.isEmpty()
    }

    def currentWordDoesNotMatchTypeWithChar(char c) {
        if (Character.isAlphabetic(c as int))
            return currentChars.stream().filter({Character.isAlphabetic(it as int)}).count() == 0
        else if (Character.isDigit(c))
            return currentChars.stream().filter({Character.isDigit(it as int)}).count() == 0
        return false
    }

    boolean partOfComplexDelimiterInBuffer(char c) {
        DELIMITERS  .stream().filter({it.value == currentWordAsString() + c}).count() == 1
    }

    def addCharToStack(char c) {
        currentChars.add(c)
    }

    def clearBuffer() {
        pushWord()
    }

    def pushWord() {
        if (!currentWordIsEmpty()) {
            def currentWord = currentWordAsString()

            def delimiter = parseDelimiter(currentWord)
            def keyword = parseKeyword(currentWord)
            def type
            def uniqueId
            if (delimiter.isPresent()) {
                type = LexicalType.SPECIAL_SYMBOL
                uniqueId = delimiter.get().uniqueId
            } else if (keyword.isPresent()) {
                type = LexicalType.KEYWORD
                uniqueId = keyword.get().uniqueId
            } else if (isNumber(currentWord)) {
                type = LexicalType.CONST
                uniqueId = identifierCounter++
            } else {
                type = LexicalType.IDENTIFIER
                uniqueId = identifierCounter++
            }


            currentChars.clear()
            lexems.add(new ParseResult(uniqueId, type, currentWord, currentPosition))
            currentPosition = cursorPosition
        }
    }

    def currentWordAsString() {
        currentChars.stream()
                .map({ it.toString() })
                .collect(Collectors.joining())
    }

    static def parseKeyword(String s) {
        KEYWORDS.stream().filter({it.value == s}).findFirst()
    }

    static def parseDelimiter(String s) {
        DELIMITERS.stream().filter({it.value == s}).findFirst()
    }
}