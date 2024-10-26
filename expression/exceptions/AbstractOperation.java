package expression.exceptions;

import expression.Expression;
import expression.ListExpression;
import expression.TripleExpression;

import java.util.List;
import java.util.Objects;

public abstract class AbstractOperation implements Expression, TripleExpression, ListExpression {
    protected Expression left, right;
    protected String operator;

    public AbstractOperation(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    protected final int evaluateExpressions(int x, int y, int z) {
        TripleExpression left = (TripleExpression) this.left;
        TripleExpression right = (TripleExpression) this.right;
        int leftResult = left.evaluate(x, y, z);
        int rightResult = right.evaluate(x, y, z);
        checkOverflow(leftResult, rightResult);
        return applyOperation(leftResult, rightResult);
    }

    protected final int evaluateExpressions(int x) {
        int leftResult = left.evaluate(x);
        int rightResult = right.evaluate(x);
        checkOverflow(leftResult, rightResult);
        return applyOperation(leftResult, rightResult);
    }


    public int evaluateExpressions(List<Integer> variables){
        ListExpression left = (ListExpression) this.left;
        ListExpression right = (ListExpression) this.right;
        int leftResult = left.evaluate(variables);
        int rightResult = right.evaluate(variables);
        checkOverflow(leftResult, rightResult);
        return applyOperation(leftResult, rightResult);
    }


    protected abstract void checkOverflow(int leftResult, int rightResult);
    protected abstract int applyOperation(int leftResult, int rightResult);
    @Override
    public String toString() {
        return "(" + this.left.toString() + " " + operator + " " + this.right.toString() + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractOperation that = (AbstractOperation) obj;
        return left.equals(that.left) && right.equals(that.right) && operator.equals(that.operator);
    }
    @Override
    public int hashCode() {
        return Objects.hash(left, right, operator);
    }
}
