package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;

import java.util.ArrayList;
import java.util.List;

public final class ParserExpression extends BaseParser {
    private int parenthesisCount = 0;
    private boolean closingBracketUsed = true;
    private List<String> variables = new ArrayList<>();

    ParserExpression(CharSource source, List<String> variables) {
        super(source);
        this.variables = variables;
    }

    ParserExpression(CharSource source) {
        super(source);
    }

    public TripleExpression parse() {
        Expression parsed = parseExpression();
        return (TripleExpression) parsed;
    }

    private Expression parseExpression() {
        return parseLowestPriority();
    }

    private Expression parseLowestPriority() {
        Expression left = parseMediumPriority();
        while (match('+') || match('-')) {
            char operator = take();
            Expression right = parseMediumPriority();
            left = (operator == '+') ? new CheckedAdd(left, right) : new CheckedSubtract(left, right);
        }
        return left;
    }

    private Expression parseMediumPriority() {
        Expression left = parseHigherPriority();
        while (match('*') || match('/')) {
            char operator = take();
            Expression right = parseHigherPriority();
            left = (operator == '*') ? new CheckedMultiply(left, right) : new CheckedDivide(left, right);
        }
        return left;
    }

    private Expression parseHigherPriority() {
        skipWhitespace();
        if (take('-')) {
            return parseNegativeOrConst();
        } else if (take('(')) {
            return parseOpenBracket();
        } else if (test('l') || test('t')) {
            return parseL0T0();
        } else {
            return parseOperand();
        }
    }

    private Expression parseL0T0() {
        char take = take();
        if (take('0') && (!test(' ') && !test('('))) {
            throw new ParsingException("t0/l0 exception");
        }
        skipWhitespace();
        Expression argument = parseHigherPriority();
        return take == 'l' ? new L0(argument) : new T0(argument);
    }

    private Expression parseNegativeOrConst() {
        if (!between('0', '9')) {
            return new CheckedNegate(parseHigherPriority());
        } else {
            return parseConst(true);
        }
    }

    private Expression parseOpenBracket() {
        parenthesisCount++;
        Expression current = parseExpression();
        if (test(')')) {
            take();
        } else {
            throw new ParsingException("No closing parenthesis");
        }
        closingBracketUsed = true;
        return current;
    }

    private Expression parseOperand() {
        if (!variables.isEmpty() && !between('0', '9')) {
            StringBuilder sb = new StringBuilder();
            while (isNotOperator() && !isWhitespace() && !test('\u0000') && !test(')')) {
                sb.append(take());
            }
            for (String variable : variables) {
                if (sb.toString().equals(variable)) {
                    return new Variable(variables.indexOf(variable));
                }
            }
        } else if (test('x') || test('y') || test('z')) {
            return new Variable(Character.toString(take()));
        }
        return parseConst(false);
    }

    private Expression parseConst(boolean isNegative) {
        skipWhitespace();
        StringBuilder str = new StringBuilder();
        if (isDigit()) {
            while (between('0', '9')) {
                str.append(take());
            }
        }
        if (str.isEmpty()) {
            throw new ParsingException("no argument");
        }
        check();
        int value;
        if (isNegative) {
            str = new StringBuilder("-" + str);
        }
        try {
            value = Integer.parseInt(str.toString());
        } catch (NumberFormatException e) {
            throw new OverflowException("overflow");
        }

        return new Const(value);
    }

    private boolean match(char expected) {
        skipWhitespace();
        closeBracketUse();
        if (!test(')') && (isNotOperator() && !isDigit())) {
            throw new ParsingException("error inserted");
        }
        return test(expected);
    }

    private void check() {
        skipWhitespace();
        if (!test('\0') && isNotOperator() && !closeBracketUse()) {
            throw new ParsingException("Error: Invalid input detected.");
        }

    }

    private boolean closeBracketUse() {
        if (test(')') && closingBracketUsed) {
            closingBracketUsed = false;
            parenthesisCount--;
            if (parenthesisCount < 0) {
                throw new ParsingException("Error: Invalid sequence detected.");
            }
            return true;
        }
        return false;
    }

    private boolean isDigit() {
        return between('0', '9') || test('\u0000');
    }

    private boolean isNotOperator() {
        return !test('+') && !test('-') && !test('*') && !test('/');
    }
}