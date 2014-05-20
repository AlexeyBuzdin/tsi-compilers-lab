package tsi.compilers.lab1

import java.util.stream.Collectors

class LexicalAnalyser {

    def lexems = new ArrayList<ParseResult>()
    def currenChars = new ArrayList<Character>()

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
        }
        pushWord()

        return lexems
    }

    boolean isDelimiter(char c) {
        ApplicationConstants.DELIMITERS.stream().filter({
            it.value.length() ==0 && it.value.charAt(0) == c
        })
        .count() == 0
    }

    boolean isWhitespace(char c) {
        c == ' '
    }

    boolean currentWordIsEmpty() {
        currenChars.isEmpty()
    }

    boolean partOfComplexDelimiterInBuffer(char c) {
        false
    }

    def addCharToStack(char c) {
        currenChars.add(c)
    }

    def pushWord() {
        if (!currentWordIsEmpty()) {
            def currentWord = currenChars.stream()
                    .map({it.toString()})
                    .collect(Collectors.joining())
            lexems.add(new ParseResult(1, LexicalType.SPECIAL_SYMBOL, currentWord, 0))
        }
    }
}