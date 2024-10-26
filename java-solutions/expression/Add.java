package expression;

import java.util.List;

public class Add extends Operation {
    public Add(Expression left, Expression right) {
        super(left, right, "+");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression left = (TripleExpression) this.left;
        TripleExpression right = (TripleExpression) this.right;
        return left.evaluate(x, y, z) + right.evaluate(x, y, z);
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) + right.evaluate(x);
    }

    @Override
    public int evaluate(List<Integer> variables) {
        ListExpression left = (ListExpression) this.left;
        ListExpression right = (ListExpression) this.right;
        int leftResult = left.evaluate(variables);
        int rightResult = right.evaluate(variables);
        return leftResult + rightResult;
    }
}
