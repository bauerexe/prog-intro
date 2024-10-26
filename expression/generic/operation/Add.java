package expression.generic.operation;


public class Add<T extends Number> extends Operation<T> {
    public Add(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right);
    }

    @Override
    public T apply(T x, T y, Calculator<T> calculator) {
        return calculator.add(x, y);
    }
}
