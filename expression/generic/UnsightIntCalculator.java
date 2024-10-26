package expression.generic;

import expression.exceptions.OverflowException;
import expression.generic.operation.Calculator;

public class UnsightIntCalculator implements Calculator<Integer> {

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
        return a + b;
    }


    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer negate(Integer a) {
        return -a;
    }

    @Override
    public Integer subtract(Integer a, Integer b){
        return a - b;
    }
}
