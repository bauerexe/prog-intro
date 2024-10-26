package expression.generic.operation;

public interface BinaryOperation<T extends Number> extends TripleExpression<T> {
     T apply(T x, T y, Calculator<T> calculator);
}
