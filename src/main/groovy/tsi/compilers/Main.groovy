package tsi.compilers

import tsi.compilers.lab1.LexicalAnalyser
import tsi.compilers.lab1.ParseResult
import tsi.compilers.lab2.Parser

class Main {

    public static void main(String[] args) {
        startLexer()
        startParser()
    }

    private static void startParser() {
        Parser.parse("while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);")
    }

    private static void startLexer() {
        def analyser = new LexicalAnalyser()
        def lexems = analyser.parseToLexems(
                "procedure TEditor.NewLine;\n" +
                        "const\n" +
                        "  CrLf: array[1..2] of Char = #13#10;\n" +
                        "var  I, P: Word;\n" +
                        "begin\n" +
                        "  P := LineStart(CurPtr);  I := P;\n" +
                        "  while (I < CurPtr) and ((Buffer^[I] = #10) or (Buffer^[I] = #9)) do Inc(I);\n" +
                        "  InsertText(@CrLf, 2, False);\n" +
                        "  if AutoIndent then InsertText(@Buffer^[P], I - P, False);\n" +
                        "end;")
        for (ParseResult result : lexems) {
            println result
        }
    }
}