package expression.exceptions;

import expression.Expression;

import java.util.List;

public class CheckedDivide extends AbstractOperation {
    public CheckedDivide(Expression left, Expression right) {
        super(left, right, "/");
    }

    @Override
    protected void checkOverflow(int leftResult, int rightResult) {
        if (rightResult == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (leftResult == Integer.MIN_VALUE && rightResult == -1) {
            throw new OverflowException("Overflow in division");
        }
    }

    @Override
    protected int applyOperation(int leftResult, int rightResult) {
        return leftResult / rightResult;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateExpressions(x, y, z);
    }

    @Override
    public int evaluate(int x) {
        return evaluateExpressions(x);
    }
    @Override
    public int evaluate(List<Integer> variables) {
        return evaluateExpressions(variables);
    }
}
