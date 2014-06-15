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

    def "Grammatics should recognize function factor"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Inc()"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.factor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize function factor with single parameter"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Inc(1)"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.factor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize function factor with multiple parameters"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Inc(1, 2)"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.factor()
        then: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should recognize function factor with multiple parameters and var"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("Inc(1, Var)"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
        when: parser.factor()
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

    def "Grammatics should support statement within loop"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (a < b) do true"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statement()
        expect: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should support loop within a loop"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (a < b) do while (a < b) do true"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statement()
        expect: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should support function call within a loop"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (a < b) do Int(1)"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statement()
        expect: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should support and expression"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (a and b) do true"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statement()
        expect: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should parse full string"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statements()
        expect: parser.numberOfSyntaxErrors == 0
    }

    def "Grammatics should fail parse if semicolon is missing"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I)"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statements()
        expect: parser.numberOfSyntaxErrors == 1
    }

    def "Grammatics should fail parse if one bracket is missing"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while (I < CurPtr) and Buffer^[I] = -10) do Inc(I);"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statements()
        expect: parser.numberOfSyntaxErrors == 1
    }

    def "Grammatics should pass parse if arrow is missing"() {
        given:
            def lexer  = new TSIGrammaticLexer(new ANTLRInputStream("while ((I < CurPtr) and Buffer[I] = -10) do Inc(I);"))
            TokenStream tokens = new CommonTokenStream(lexer)
            def parser = new TSIGrammaticParser(tokens)
            parser.statements()
        expect: parser.numberOfSyntaxErrors == 0
    }
}