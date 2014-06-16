package tsi.compilers.quadruples

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import tsi.compilers.grammatics.TSIGrammaticLexer
import tsi.compilers.grammatics.TSIGrammaticParser

class QuadrupleGenerator {

    def generate(String str) {
        def stream = new ANTLRInputStream(str)
        def lexer = new TSIGrammaticLexer(stream)
        TokenStream tokens = new CommonTokenStream(lexer)

        def quarters = new ArrayList<Quadruple>()

        def parser = new TSIGrammaticParser(tokens)
        parser.addParseListener(new QuartersTSIGrammaticListener(quarters))
        parser.statements()

        return quarters
    }
}
