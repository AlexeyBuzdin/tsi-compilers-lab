package tsi.compilers

import tsi.compilers.parser.Parser
import tsi.compilers.quadruples.QuadrupleGenerator

class FullSystemRun {

    public static void main(String[] args) {
        def string = "while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);"

        def parser = new Parser()
        def validCode = parser.isValid(string)

        if (validCode) {
            def generator = new QuadrupleGenerator()
            def quarters = generator.generate(string)

            for (Object quarter : quarters) {
                println quarter
            }
        }
    }
}
