package expression.generic.operation;

public class Subtract<T extends Number> extends Operation<T> {
    public Subtract(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right);
    }

    @Override
    public T apply(T x, T y, Calculator<T> calculator){
        return calculator.subtract(x, y);
    }
}