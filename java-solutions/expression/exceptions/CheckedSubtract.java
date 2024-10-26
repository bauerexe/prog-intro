package expression.exceptions;

import expression.Expression;

import java.util.List;

public class CheckedSubtract extends AbstractOperation {
    public CheckedSubtract(Expression left, Expression right) {
        super(left, right, "-");
    }

    @Override
    protected void checkOverflow(int leftResult, int rightResult) {
        if (((leftResult >= 0 && rightResult < 0) && (leftResult - rightResult < 0)) ||
                ((leftResult < 0 && rightResult >= 0) && (leftResult - rightResult >= 0))) {
            throw new OverflowException("Overflow in subtraction");
        }
    }

    @Override
    protected int applyOperation(int leftResult, int rightResult) {
        return leftResult - rightResult;
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
