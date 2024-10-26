package expression.generic.operation;

public interface TripleExpression<T extends Number> {
    T evaluate(T x, T y, T z, Calculator<T> calculator);
}
