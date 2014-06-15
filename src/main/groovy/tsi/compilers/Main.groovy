package tsi.compilers

import tsi.compilers.analyzer.LexicalAnalyser
import tsi.compilers.common.ParseResult
import tsi.compilers.parser.Parser
import tsi.compilers.quarters.QuarterGenerator

class Main {

    public static void main(String[] args) {
        startLexer()
        startParser()
        startQuarters();
    }

    private static void startParser() {
        new Parser().isValid("while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);")
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

    private static void startQuarters() {
        def analyser = new LexicalAnalyser()
        def lexems = analyser.parseToLexems("while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);")

        def generator = new QuarterGenerator()
        def results = generator.generate(lexems)

        for (ParseResult result : results) {
            println result
        }
    }
}