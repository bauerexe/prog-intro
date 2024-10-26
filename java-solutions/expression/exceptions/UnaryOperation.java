package expression.exceptions;

import expression.Expression;
import expression.ListExpression;
import expression.TripleExpression;

import java.util.Objects;

public abstract class UnaryOperation implements Expression, TripleExpression, ListExpression {
    protected Expression expression;
    protected String operator;

    public UnaryOperation(Expression expression, String operator) {
        this.expression = expression;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator +  "(" + this.expression.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UnaryOperation that = (UnaryOperation) obj;
        return expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, operator, getClass());
    }
}
