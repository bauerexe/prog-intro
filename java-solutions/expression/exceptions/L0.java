package expression.exceptions;

import expression.Expression;
import expression.ListExpression;
import expression.TripleExpression;

import java.util.List;

public class L0 extends UnaryOperation {
    public L0(Expression expression) {
        super(expression, "l0");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression expression = (TripleExpression) this.expression;
        return Integer.numberOfLeadingZeros(expression.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfLeadingZeros(expression.evaluate(x));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        ListExpression expression = (ListExpression) this.expression;
        return expression.evaluate(variables);
    }
}

