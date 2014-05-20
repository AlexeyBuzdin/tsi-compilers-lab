package tsi.compilers.lab1

import spock.lang.Ignore
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

    def "Single special symbol should be returned as a parsed result"() {
        when: def lexems = analyser.parseToLexems(".")
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.uniqueId == 1
            parseResult.position == 0
            parseResult.value == "."
            parseResult.type == LexicalType.SPECIAL_SYMBOL
    }

    @Ignore
    def "Identifier should be returned as a result"() {
        def identifier = "TEditor"
        when: def lexems = analyser.parseToLexems(identifier)
        then:
            lexems.size() == 1

            def parseResult = lexems.get(0)
            parseResult.uniqueId == 1
            parseResult.position == 0
            parseResult.value == identifier
            parseResult.type == LexicalType.IDENTIFIER
    }
}
