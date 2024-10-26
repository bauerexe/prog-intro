package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements Expression, TripleExpression, ListExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }
    public Variable(int number) {
        this.name = "$" + number;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Variable variable = (Variable) obj;
        return Objects.equals(name, variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalArgumentException("Unknown variable: " + name);
        };
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return variables.get(Integer.parseInt(name.substring(1)));
    }
}
