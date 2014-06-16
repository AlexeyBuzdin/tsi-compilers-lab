package tsi.compilers.quadruples

import spock.lang.Specification
import tsi.compilers.analyzer.LexicalAnalyser

class QuadrupleGeneratorTest extends Specification {

    def generator = new QuadrupleGenerator()

    def "No result should be generated for single expression"() {
        when: def quadruples = generator.generate(";")
        then: quadruples.size() == 0
    }

    def "Function invocation quadruple should be returned"() {
        when: def quadruples = generator.generate("Int();")
        then: quadruples.size() == 1
    }

    def "Function invocation and param quadruples should be returned"() {
        when: def quadruples = generator.generate("Int(1);")
        then: quadruples.size() == 2
    }

    def "While block should generate two quadruples"() {
        when: def quadruples = generator.generate("while(I < 0) do ;")
        then: quadruples.size() == 2
    }

    def "While block with function call"() {
        when: def quadruples = generator.generate("while(I < 0) do Inc();")
        then: quadruples.size() == 3
    }

    def "While block with strange buffer"() {
        when: def quadruples = generator.generate("while(Buffer^[I] = -10) do Inc();")
        then: quadruples.size() == 3
    }

    def "Full program"() {
        when: def quadruples = generator.generate("while ((I < CurPtr) and Buffer^[I] = -10) do Inc(I);")
        then: quadruples.size() == 7
    }
}
