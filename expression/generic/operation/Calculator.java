package expression.generic.operation;

public interface Calculator<T extends Number> {
    T fromNumber(Number value);
    T fromString(String value);
    T add(T x, T y);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T y);
    T negate(T x);
}
