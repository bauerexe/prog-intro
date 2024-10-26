
package expression.generic;

import expression.exceptions.OverflowException;
import expression.generic.operation.Calculator;

public class IntCalculator implements Calculator<Integer> {

    @Override
    public Integer fromNumber(Number value) {
        return value.intValue();
    }

    @Override
    public Integer fromString(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public Integer add(Integer a, Integer b) {
        if (((a > 0 && b > 0) && (a + b < 0)) ||
                ((a < 0 && b < 0) && (a + b >= 0))) {
            throw new OverflowException("Overflow in addition");
        }
        return a + b;
    }


    @Override
    public Integer multiply(Integer a, Integer b) {
        if ((b > 0 && (a > Integer.MAX_VALUE / b || a < Integer.MIN_VALUE / b)) ||
                (b < 0 && (b == -1 ? a == Integer.MIN_VALUE : a > Integer.MIN_VALUE / b || a < Integer.MAX_VALUE / b))) {
            throw new OverflowException("Overflow in multiplication");
        }
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException("Overflow in division");
        }
        return a / b;
    }

    @Override
    public Integer negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -a;
    }

    @Override
    public Integer subtract(Integer a, Integer b){
        if (((a >= 0 && b < 0) && (a - b < 0)) ||
                ((a < 0 && b >= 0) && (a - b >= 0))) {
            throw new OverflowException("Overflow in subtraction");
        }
        return a - b;
    }
}
