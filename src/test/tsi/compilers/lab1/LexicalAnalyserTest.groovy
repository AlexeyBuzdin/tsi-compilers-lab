package tsi.compilers.lab1

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

    def "Identifier should be returned"() {
        def identifier = "TEditor"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.uniqueId == 31
            parseResult.position == 0
            parseResult.value == identifier
            parseResult.type == LexicalType.IDENTIFIER
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
}
