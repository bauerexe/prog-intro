package expression.generic.operation;

public interface TripleParser<T extends Number> {
    TripleExpression<T> parse(String expression) throws Exception;
}
