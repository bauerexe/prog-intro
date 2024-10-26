package expression.exceptions;


import expression.Expression;
import expression.ListExpression;
import expression.TripleExpression;

import java.util.List;

public class T0 extends UnaryOperation {
    public T0(Expression expression) {
        super(expression, "t0");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression expression = (TripleExpression) this.expression;
        return Integer.numberOfTrailingZeros(expression.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfTrailingZeros(expression.evaluate(x));
    }

    @Override
    public int evaluate(List<Integer> variables) {
        ListExpression expression = (ListExpression) this.expression;
        return expression.evaluate(variables);
    }
}
