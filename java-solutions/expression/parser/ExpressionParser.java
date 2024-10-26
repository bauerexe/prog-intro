package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {
    public static void main(String[] args) {
        String str = "1 + 2 * (1 ^ 7)";
        System.out.println(new ExpressionParser().parse(str));
    }

    @Override
    public TripleExpression parse(String expression) {
        return new ParserExpression(new StringSource(expression)).parse();
    }
}