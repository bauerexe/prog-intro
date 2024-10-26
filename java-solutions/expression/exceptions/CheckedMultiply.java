package expression.exceptions;

import expression.Expression;

import java.util.List;

public class CheckedMultiply extends AbstractOperation {
    public CheckedMultiply(Expression left, Expression right) {
        super(left, right, "*");
    }

    @Override
    protected void checkOverflow(int leftResult, int rightResult) {
        if (rightResult > 0 ? leftResult > Integer.MAX_VALUE / rightResult || leftResult < Integer.MIN_VALUE / rightResult
                : (rightResult < -1 ? leftResult > Integer.MIN_VALUE / rightResult || leftResult < Integer.MAX_VALUE / rightResult
                : rightResult == -1 && leftResult == Integer.MIN_VALUE)) {
            throw new OverflowException("Overflow in multiplication");
        }
    }

    @Override
    protected int applyOperation(int leftResult, int rightResult) {
        return leftResult * rightResult;
    }

    @Override
    public int evaluate(int x) {
        return evaluateExpressions(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateExpressions(x, y, z);
    }
    @Override
    public int evaluate(List<Integer> variables) {
        return evaluateExpressions(variables);
    }
}
