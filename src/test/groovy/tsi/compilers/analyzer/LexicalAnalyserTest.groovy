package tsi.compilers.analyzer

import spock.lang.Specification

class LexicalAnalyserTest extends Specification {

    private LexicalAnalyser analyser = new LexicalAnalyser()

    def "Empty string should not throw any errors"() {
        when: def lexems = analyser.parseToLexems("")
        then: lexems.empty
    }

    def "Blank string should not throw any errors"() {
        when: def lexems = analyser.parseToLexems("      ")
        then: lexems.empty
    }

    def "Single special symbol should be returned"() {
        when: def lexems = analyser.parseToLexems(".")
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.value == "."
            parseResult.type == LexicalType.SPECIAL_SYMBOL
    }

    def "@ symbol should be returned as a parsed result with uniqueId 17"() {
        when: def lexems = analyser.parseToLexems("@")
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.uniqueId == 17
            parseResult.position == 0
            parseResult.value == "@"
            parseResult.type == LexicalType.SPECIAL_SYMBOL
    }

    def "Number should be recognized as constant"() {
        when: def lexems = analyser.parseToLexems("1")
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.uniqueId == 100
            parseResult.position == 0
            parseResult.value == "1"
            parseResult.type == LexicalType.CONST
    }

    def "Identifier should be returned"() {
        def identifier = "TEditor"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.uniqueId == 100
            parseResult.position == 0
            parseResult.value == identifier
            parseResult.type == LexicalType.IDENTIFIER
    }

    def "Position should be correct for more than one lexem"() {
        def identifier = "TEditor somethingelse"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 2

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.value == "TEditor"
            parseResult1.type == LexicalType.IDENTIFIER

            def parseResult2 = lexems.get(1)
            parseResult2.position == 8
            parseResult2.value == "somethingelse"
            parseResult2.type == LexicalType.IDENTIFIER
    }

    def "Position should be correct for multiple whitespace"() {
        def identifier = "TEditor   somethingelse"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 2

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.value == "TEditor"
            parseResult1.type == LexicalType.IDENTIFIER

            def parseResult2 = lexems.get(1)
            parseResult2.position == 10
            parseResult2.value == "somethingelse"
            parseResult2.type == LexicalType.IDENTIFIER
    }

    def "Position should be correct if starts from whitespace"() {
        def identifier = "   TEditor"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult1 = lexems.get(0)
            parseResult1.position == 3
            parseResult1.value == "TEditor"
            parseResult1.type == LexicalType.IDENTIFIER
    }

    def "Two symbols should be returned"() {
        def identifier = "@."
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 2

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.uniqueId == 17
            parseResult1.value == "@"
            parseResult1.type == LexicalType.SPECIAL_SYMBOL

            def parseResult2 = lexems.get(1)
            parseResult2.position == 1
            parseResult2.uniqueId == 16
            parseResult2.value == "."
            parseResult2.type == LexicalType.SPECIAL_SYMBOL
    }

    def "Keyword should be returned"() {
        def identifier = "procedure"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.uniqueId == 1
            parseResult1.value == identifier
            parseResult1.type == LexicalType.KEYWORD
    }

    def "Delimiter should not be merged with identifier"() {
        def identifier = ".NewLine"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 2

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.uniqueId == 16
            parseResult1.value == "."
            parseResult1.type == LexicalType.SPECIAL_SYMBOL

            def parseResult2 = lexems.get(1)
            parseResult2.position == 1
            parseResult2.uniqueId == 100
            parseResult2.value == "NewLine"
            parseResult2.type == LexicalType.IDENTIFIER
    }

    def "Digits should be merged with together"() {
        def identifier = "12"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.uniqueId == 100
            parseResult1.value == identifier
            parseResult1.type == LexicalType.CONST
    }

    def "Complex special symbols should be recognized"() {
        def identifier = ".."
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult1 = lexems.get(0)
            parseResult1.position == 0
            parseResult1.uniqueId == 21
            parseResult1.value == identifier
            parseResult1.type == LexicalType.SPECIAL_SYMBOL
    }

    def "Unregistered symbol should throw an exception"() {
        def identifier = "\$"
        when: analyser.parseToLexems(identifier)
        then: def e = thrown(IllegalArgumentException)
              e.message == '$ at position 0 is illegal'
    }
}
