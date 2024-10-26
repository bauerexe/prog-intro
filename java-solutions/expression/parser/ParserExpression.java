package expression.parser;

import expression.*;

class ParserExpression extends BaseParser {
    protected ParserExpression(CharSource source) {
        super(source);
    }

    public TripleExpression parse() {
        return (TripleExpression) parseExpression();
    }

    private Expression parseExpression() {
        return parseOr();
    }

    private Expression parseOr() {
        Expression left = parseXor();
        while (match('|')) {
            take();
            Expression right = parseOr();
            left = new Or(left, right);
        }
        return left;
    }

    private Expression parseXor() {
        Expression left = parseAnd();
        while (match('^')) {
            take();
            Expression right = parseAnd();
            left = new Xor(left, right);
        }
        return left;
    }

    private Expression parseAnd() {
        Expression left = parseLowestPriority();
        while (match('&')) {
            take();
            Expression right = parseLowestPriority();
            left = new And(left, right);
        }
        return left;
    }

    private Expression parseLowestPriority() {
        Expression left = parseMediumPriority();
        while (match('+') || match('-')) {
            char operator = take();
            Expression right = parseMediumPriority();
            left = (operator == '+') ? new Add(left, right) : new Subtract(left, right);
        }
        return left;
    }

    private Expression parseMediumPriority() {
        Expression left = parseHigherPriority();
        while (match('*') || match('/')) {
            char operator = take();
            Expression right = parseHigherPriority();
            left = (operator == '*') ? new Multiply(left, right) : new Divide(left, right);
        }
        return left;
    }

    private Expression parseHigherPriority() {
        skipWhitespace();
        if (take('-')) {
            if (!between('0', '9')) {
                return new UnaryMinus(parseHigherPriority());
            } else {
                return parseConst(true);
            }
        } else if (take('(')) {
            Expression current = parseExpression();
            expect(')');
            return current;
        } else {
            return parseOperand();
        }
    }

    private Expression parseOperand() {
        if (test('x') || test('y') || test('z')) {
            return new Variable(Character.toString(take()));
        } else {
            return parseConst(false);
        }
    }

    private Expression parseConst(boolean isNegative) {
        skipWhitespace();
        StringBuilder str = new StringBuilder("" + take());
        while (between('0', '9')) {
            str.append(take());
        }
        long value = Long.parseLong(str.toString());
        if (isNegative) {
            return new Const((int) -value);
        }
        return new Const((int) value);
    }

    private boolean match(char expected) {
        skipWhitespace();
        return test(expected);
    }
}