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
        when: parser.expressionOperator()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize variable as factor"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Hello"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.factor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize simple expression"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("I < CurPtr"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.expression()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize signed numbers with minus"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("-10"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.signedFactor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize signed numbers with two minuses"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("--10"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.signedFactor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should have an error on three minuses"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("---10"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.signedFactor()
        then: parser.numberOfSyntaxErrors == 1
    }

    def "Grammatics should recognize signed numbers without sign"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("10"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.signedFactor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize variable with dimensionQualifier number in brackets"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Buffer^[1]"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.variable()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize variable with dimensionQualifier identifier in brackets"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Buffer^[I]"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.variable()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize method expression"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Inc(I)"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.expression()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize variable with dimensionQualifier method in brackets"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Buffer^[Inc(I)]"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.variable()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should support infinite loop with statement"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (true) do true"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.code()
        expect: parser.numberOfSyntaxErrors == 0
    }
}