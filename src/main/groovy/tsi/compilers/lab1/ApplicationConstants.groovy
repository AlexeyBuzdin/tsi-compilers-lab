package tsi.compilers.lab1

class ApplicationConstants {

    public static final DELIMITERS = Arrays.asList(
            new Keyword(16, "."),
            new Keyword(17, "@"),
            new Keyword(18, ":"),
            new Keyword(19, "["),
            new Keyword(20, "]"),
            new Keyword(21, ".."),
            new Keyword(22, "="),
            new Keyword(23, ";"),
            new Keyword(24, ","),
            new Keyword(25, ":="),
            new Keyword(26, "("),
            new Keyword(27, ")"),
            new Keyword(28, "<"),
            new Keyword(29, "^"),
            new Keyword(30, "-"),
            new Keyword(31, "'"),
    )

    public static final KEYWORDS = Arrays.asList(
            new Keyword(1, "procedure"),
            new Keyword(2, "const"),
            new Keyword(3, "array"),
            new Keyword(4, "of"),
            new Keyword(5, "var"),
            new Keyword(6, "begin"),
            new Keyword(7, "while"),
            new Keyword(8, "end"),
            new Keyword(9, "do"),
            new Keyword(10, "if"),
            new Keyword(11, "then"),
            new Keyword(12, "and"),
            new Keyword(13, "or"),
            new Keyword(14, "Char"),
            new Keyword(15, "False"),
    )
}
