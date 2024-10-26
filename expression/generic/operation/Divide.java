package expression.generic.operation;


public class Divide<T extends Number> extends Operation<T> {
    public Divide(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right);
    }

    @Override
    public T apply(T x, T y, Calculator<T> calculator) {
        return calculator.divide(x, y);
    }
}