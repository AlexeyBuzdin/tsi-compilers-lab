package tsi.compilers.lab2

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.TokenStream
import spock.lang.Specification
import tsi.compilers.grammatics.TSIGrammaticLexer
import tsi.compilers.grammatics.TSIGrammaticParser

class TSIGrammaticParserTest extends Specification {

    def "Grammatics should not throw an error on less sign"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("<"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.errorHandler = new ExceptionErrorStrategy()
        when: parser.expressionOperator()
        then: notThrown(Exception)
    }

    def "Grammatics should recognize variable as factor"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Hello"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.errorHandler = new ExceptionErrorStrategy()
        when: parser.factor()
        then: notThrown(Exception)
    }

    def "Grammatics should recognize simple expression"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("I < CurPtr"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.errorHandler = new ExceptionErrorStrategy()
        when: parser.expression()
        then: notThrown(Exception)
    }

    def "Grammatics should support infinite loop with statement"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (true) do true"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.errorHandler = new ExceptionErrorStrategy()
            def code = parser.code()
        expect: notThrown(Exception)
    }
}