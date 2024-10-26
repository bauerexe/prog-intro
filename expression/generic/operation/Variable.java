package expression.generic.operation;


import java.util.List;

public class Variable<T extends Number> implements TripleExpression<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x, T y, T z, Calculator<T> calculator) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException("Unknown variable: " + name);
        };
    }
}