package expression.generic.operation;


import expression.exceptions.ParsingException;
import expression.parser.BaseParser;


import expression.parser.CharSource;

public class ExpressionParser<T extends Number> extends BaseParser implements TripleParser<T> {
    private int parenthesisCount = 0;
    private boolean closingBracketUsed = true;

    private final Calculator<T> calculator;

    public ExpressionParser(CharSource source, Calculator<T> calculator) {
        super(source);
        this.calculator = calculator;
    }

    @Override
    public TripleExpression<T> parse(String expression) throws Exception {
        return parseExpression();
    }

    private TripleExpression<T> parseExpression() {
        return parsePriority(1);
    }

    private TripleExpression<T> parsePriority(int priority) {
        TripleExpression<T> left;
        if (priority <= 3) {
            left = parsePriority(priority + 1);
        } else {
            left = parseHigherPriority();
        }

        while (match(getOperation(priority))) {
            take();
            TripleExpression<T> right;
            if (priority <= 3) {
                right = parsePriority(priority + 1);
            } else {
                right = parseHigherPriority();
            }
            left = getException(priority, left, right);
        }
        return left;
    }

    char getOperation(int priority) {
        return switch (priority) {
            case 1 -> '+';
            case 2 -> '-';
            case 3 -> '*';
            case 4 -> '/';
            default -> throw new IllegalArgumentException("Unsupported priority: " + priority);
        };
    }

    TripleExpression<T> getException(int priority, TripleExpression<T> left, TripleExpression<T> right) {
        return switch (priority) {
            case 1 -> new Add<>(left, right);
            case 2 -> new Subtract<>(left, right);
            case 3 -> new Multiply<>(left, right);
            case 4 -> new Divide<>(left, right);
            default -> throw new IllegalArgumentException("Unsupported priority: " + priority);
        };
    }


    private TripleExpression<T> parseHigherPriority() {
        skipWhitespace();
        if (take('-')) {
            return parseNegativeOrConst();
        } else if (take('(')) {
            return parseOpenBracket();
        } else {
            return parseOperand();
        }
    }

    private TripleExpression<T> parseNegativeOrConst() {
        if (!between('0', '9')) {
            return new Negate<>(parseHigherPriority());
        } else {
            return parseConst(true);
        }
    }

    private TripleExpression<T> parseOpenBracket() {
        parenthesisCount++;
        TripleExpression<T> current = parseExpression();
        if (test(')')) {
            take();
        } else {
            throw new ParsingException("No closing parenthesis");
        }
        closingBracketUsed = true;
        return current;
    }

    private TripleExpression<T> parseOperand() {
        if (test('x') || test('y') || test('z')) {
            return new Variable<>(Character.toString(take()));
        }
        return parseConst(false);
    }

    private TripleExpression<T> parseConst(boolean isNegative) {
        skipWhitespace();
        StringBuilder str = new StringBuilder();
        if (isDigit()) {
            while (between('0', '9')) {
                str.append(take());
            }
        }
        check();
        if (isNegative) {
            str = new StringBuilder("-" + str);
        }
        return new Const<>(calculator.fromString(str.toString()));
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
