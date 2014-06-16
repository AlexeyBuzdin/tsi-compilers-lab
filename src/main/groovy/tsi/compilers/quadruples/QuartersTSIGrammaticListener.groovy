package tsi.compilers.quadruples

import org.antlr.v4.runtime.misc.NotNull
import tsi.compilers.grammatics.TSIGrammaticBaseListener
import tsi.compilers.grammatics.TSIGrammaticParser

class QuartersTSIGrammaticListener extends TSIGrammaticBaseListener {

    Stack<Quadruple> stack = new Stack<>()
    List<Quadruple> quarters
    int curIndex

    int whileIndex;

    boolean inWhile
    boolean inFunction
    boolean inArgumentList

    public QuartersTSIGrammaticListener(List<Quadruple> quarters) {
        this.quarters = quarters
    }

    @Override
    void exitExpression(@NotNull TSIGrammaticParser.ExpressionContext ctx) {
        if (inArgumentList) {
            quarters.add(new Quadruple(){{
                index = curIndex++
                operator = "PAR"
                operand1 = ctx.getText()
            }})
        }
        if (inWhile && ctx.getChildCount() == 3)   {
            quarters.add(new Quadruple(){{
                index = curIndex++
                operator = ctx.getChild(1).getText().equals("<") ? "BL" : "BE";
                operand1 = curIndex+2

                // It's 4 AM, I am tired, and I want to sleep :(
                def text = ctx.getChild(0).getText()
                def text2 = ctx.getChild(2).getText()
                operand2 = text.contains("and") ? text.split("and")[1] : text
                operand3 = text2.contains("and") ? text2.split("and")[1] : text2
            }});
            quarters.add(new Quadruple(){{
                index = curIndex++
                operator = "BR"
                operand1 = 100
            }});
        }
    }

    @Override
    void enterMemberAccessStatement(@NotNull TSIGrammaticParser.MemberAccessStatementContext ctx) {
        inFunction = true
    }

    @Override
    void exitMemberAccessStatement(@NotNull TSIGrammaticParser.MemberAccessStatementContext ctx) {
        inFunction = false
        quarters.add(stack.pop())
    }

    @Override
    void enterArgumentList(@NotNull TSIGrammaticParser.ArgumentListContext ctx) {
        inArgumentList = true
    }

    @Override
    void exitArgumentList(@NotNull TSIGrammaticParser.ArgumentListContext ctx) {
        inArgumentList = false
    }

    @Override
    void exitIdentifier(@NotNull TSIGrammaticParser.IdentifierContext ctx) {
        if (inFunction && !inArgumentList)   {
            stack.push(new Quadruple(){{
                index = curIndex++
                operator = "CALL"
                operand1 = ctx.getText()
            }});
        }
    }

    @Override
    void enterWhileStatement(@NotNull TSIGrammaticParser.WhileStatementContext ctx) {
        inWhile = true
        whileIndex = curIndex;
    }

    @Override
    void exitWhileStatement(@NotNull TSIGrammaticParser.WhileStatementContext ctx) {
        inWhile = false
        quarters.add(new Quadruple(){{
            index = curIndex++
            operator = "BR"
            operand1 = whileIndex
        }} )
    }
}
