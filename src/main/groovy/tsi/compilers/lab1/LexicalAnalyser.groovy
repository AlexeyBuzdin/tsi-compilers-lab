package tsi.compilers.lab1

import java.util.stream.Collectors

import static tsi.compilers.lab1.ApplicationConstants.DELIMITERS

class LexicalAnalyser {

    def cursor = -1;
    def lexems = new ArrayList<ParseResult>()
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
            } else {
                addCharToStack(c)
            }
            cursor++
        }
        pushWord()

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

    boolean partOfComplexDelimiterInBuffer(char c) {
        false
    }

    def addCharToStack(char c) {
        currentChars.add(c)
    }

    def pushWord() {
        if (!currentWordIsEmpty()) {
            def currentWord = currentChars.stream()
                    .map({it.toString()})
                    .collect(Collectors.joining())

            def delimiter = parseDelimiter(currentWord)
            def type = delimiter.isPresent() ? LexicalType.SPECIAL_SYMBOL : LexicalType.IDENTIFIER
            def uniqueId = delimiter.isPresent() ? delimiter.get().uniqueId : 31

            currentChars.clear()
            lexems.add(new ParseResult(uniqueId, type, currentWord, cursor))
        }
    }

    static def parseDelimiter(String s) {
        DELIMITERS.stream().filter({it.value == s}).findFirst()
    }
}