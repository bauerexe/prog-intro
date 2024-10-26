package expression;

public class UnaryMinus implements Expression, TripleExpression {
    private final Expression operand;

    public UnaryMinus(Expression operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        String operandStr = operand.toMiniString();
        return "-(" + operandStr + ")";

    }

    @Override
    public int evaluate(int x) {
        return -x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        TripleExpression operand = (TripleExpression) this.operand;
        return -operand.evaluate(x, y, z);
    }
}
