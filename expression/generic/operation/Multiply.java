package expression.generic.operation;


public class Multiply<T extends Number> extends Operation<T> {
    public Multiply(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right);
    }

    @Override
    public T apply(T x, T y, Calculator<T> calculator) {
        return calculator.multiply(x, y);
    }
}