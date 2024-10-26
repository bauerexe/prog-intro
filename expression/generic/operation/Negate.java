package expression.generic.operation;


public class Negate<T extends Number> implements TripleExpression<T>  {
    TripleExpression<T> operand;

    public Negate(TripleExpression<T> operand) {
        this.operand = operand;
    }

    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator) {
        return calculator.negate(operand.evaluate(x, y, z, calculator));
    }
}
