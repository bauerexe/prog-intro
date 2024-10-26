package expression;

public class Const implements Expression, TripleExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Const aConst = (Const) obj;
        return value == aConst.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }
}
