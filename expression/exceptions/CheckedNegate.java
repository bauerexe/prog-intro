package expression.exceptions;

import expression.Expression;
import expression.ListExpression;
import expression.TripleExpression;

import java.util.List;

public class CheckedNegate implements Expression, TripleExpression, ListExpression {
    private final Expression operand;

    public CheckedNegate(Expression operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        String operandStr = operand.toMiniString();
        return "-(" + operandStr + ")";

    }

    @Override
    public int evaluate(int x) {
        return -x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression operand = (TripleExpression) this.operand;
        if (operand.evaluate(x, y, z) == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -operand.evaluate(x, y, z);
    }
    @Override
    public int evaluate(List<Integer> variables) {
        ListExpression operand = (ListExpression) this.operand;
        if (operand.evaluate(variables) == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -operand.evaluate(variables);
    }
}

