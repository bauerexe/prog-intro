package expression.exceptions;

import expression.ListExpression;
import expression.TripleExpression;
import expression.parser.StringSource;

import java.util.List;

public class ExpressionParser implements TripleParser, ListParser {
    public static void main(String[] args) {
        System.out.println(new ExpressionParser().parse("-2147483648").evaluate(1, 2, 3));
    }

    @Override
    public TripleExpression parse(String expression) {
        return new ParserExpression(new StringSource(expression)).parse();
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) {
        return (ListExpression) new ParserExpression(new StringSource(expression), variables).parse();
    }
}
