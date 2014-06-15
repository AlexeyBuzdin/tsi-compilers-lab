package tsi.compilers.lab2

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import tsi.compilers.grammatics.TSIGrammaticLexer
import tsi.compilers.grammatics.TSIGrammaticParser

class Parser {

    static def parse(String s) {
        def stream = new ANTLRInputStream(s)
        def lexer  = new TSIGrammaticLexer(stream)
        TokenStream tokens = new CommonTokenStream(lexer)
        def parser = new TSIGrammaticParser(tokens)
        parser.statements()
        return parser.state
    }
}
