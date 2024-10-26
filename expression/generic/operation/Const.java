package expression.generic.operation;

public class Const<T extends Number> implements TripleExpression<T> {
    private final T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator) {
        return value;
    }
}
