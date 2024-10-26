package expression.generic.operation;


public abstract class Operation<T extends Number> implements BinaryOperation<T> {
    protected final TripleExpression<T> left;
    protected final TripleExpression<T> right;

    protected Operation(TripleExpression<T> left, TripleExpression<T> right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator) {
        return apply(left.evaluate(x, y, z, calculator), right.evaluate(x, y, z, calculator), calculator);
    }
}
