package tsi.compilers

import tsi.compilers.analyzer.LexicalAnalyser
import tsi.compilers.parser.Parser
import tsi.compilers.quarters.QuarterGenerator

class FullSystemRun {

    public static void main(String[] args) {
        def string = "while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);"

        def analyser = new LexicalAnalyser()
        def lexems = analyser.parseToLexems(string)

        def parser = new Parser()
        def validCode = parser.isValid(string)

        if (validCode) {
            def generator = new QuarterGenerator()
            def quarters = generator.generate(lexems)

            for (Object quarter : quarters) {
                println quarter
            }
        }
    }
}
