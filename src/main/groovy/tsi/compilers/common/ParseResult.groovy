package tsi.compilers.common

class ParseResult {

    int uniqueId;
    LexicalType type;
    String value;
    int position;

    ParseResult(int uniqueId, LexicalType type, String value, int position) {
        this.uniqueId = uniqueId
        this.type = type
        this.value = value
        this.position = position
    }


    @Override
    public String toString() {
        return position + " | " + value + " | " + type + " | " + uniqueId;
    }
}