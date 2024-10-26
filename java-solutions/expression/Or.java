package expression;

public class Or extends Operation {
    public Or(Expression left, Expression right) {
        super(left, right, "|");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression left = (TripleExpression) this.left;
        TripleExpression right = (TripleExpression) this.right;
        return left.evaluate(x, y, z) | right.evaluate(x, y, z);
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) | right.evaluate(x);
    }
}
