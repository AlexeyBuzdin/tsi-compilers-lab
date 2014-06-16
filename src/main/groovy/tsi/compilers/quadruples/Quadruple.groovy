package tsi.compilers.quadruples

class Quadruple {
    def index
    def operator
    def operand1
    def operand2
    def operand3


    @Override
    public String toString() {
        return index    + " | " +
               operator + " | " +
               operand1 + " | " +
               operand2 + " | " +
               operand3 + "\n";
    }
}
