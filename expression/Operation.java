package expression;

import java.util.Objects;

public abstract class Operation implements Expression, TripleExpression, ListExpression {
    protected Expression left, right;
    protected String operator;

    public Operation(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + " " + operator + " " + this.right.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Operation that = (Operation) obj;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, operator, getClass());
    }
}
