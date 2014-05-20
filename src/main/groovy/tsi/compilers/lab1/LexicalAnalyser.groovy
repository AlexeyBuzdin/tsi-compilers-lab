package tsi.compilers.lab1

class LexicalAnalyser {

    def results = new ArrayList<ParseResult>()

    public List<ParseResult> parseToLexems(String string) {

        for (char c : string.chars) {
            if (!isDelimiter (c)) {
                addCharToStack(c)
            } else if (currentWordIsEmpty()) {
                pushWord()
            }
        }

        return results
    }

    boolean isDelimiter(char c) {
        c == ' '
    }

    boolean currentWordIsEmpty() {
        true
    }

    def addCharToStack(char c) {

    }

    def pushWord() {

    }
}