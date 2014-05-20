package tsi.compilers.lab1

import java.util.stream.Collectors

import static tsi.compilers.lab1.ApplicationConstants.DELIMITERS
import static tsi.compilers.lab1.ApplicationConstants.KEYWORDS

class LexicalAnalyser {

    def cursorPosition = 0
    def identifierCounter = 100
    def lexems = new ArrayList<ParseResult>()

    def currentPosition = 0
    def currentChars = new ArrayList<Character>()

    public List<ParseResult> parseToLexems(String string) {

        for (char c : string.chars) {
            if (isDelimiter(c)){
                if (partOfComplexDelimiterInBuffer(c)) {
                    addCharToStack(c)
                } else {
                    pushWord()
                    addCharToStack(c)
                }
            } else if (isWhitespace(c)) {
                pushWord()
                currentPosition = cursorPosition+1
            } else if (currentWordIsNotAlpha()) {
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

    static boolean isDelimiter(char c) {
        DELIMITERS.stream().filter({
            it.value.length() == 1 && it.value.charAt(0) == c
        })
        .count() == 1
    }

    static boolean isWhitespace(char c) {
        Character.isWhitespace(c)
    }

    boolean currentWordIsEmpty() {
        currentChars.isEmpty()
    }

    def currentWordIsNotAlpha() {
        currentChars.stream().filter({Character.isAlphabetic(it as int)}).count() == 0
    }

    boolean partOfComplexDelimiterInBuffer(char c) {
        false
    }

    def addCharToStack(char c) {
        currentChars.add(c)
    }

    def clearBuffer() {
        pushWord()
    }

    def pushWord() {
        if (!currentWordIsEmpty()) {
            def currentWord = currentChars.stream()
                    .map({it.toString()})
                    .collect(Collectors.joining())

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
            } else {
                type = LexicalType.IDENTIFIER
                uniqueId = identifierCounter++
            }


            currentChars.clear()
            lexems.add(new ParseResult(uniqueId, type, currentWord, currentPosition))
            currentPosition = cursorPosition
        }
    }

    static def parseKeyword(String s) {
        KEYWORDS.stream().filter({it.value == s}).findFirst()
    }

    static def parseDelimiter(String s) {
        DELIMITERS.stream().filter({it.value == s}).findFirst()
    }
}