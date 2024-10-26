package expression.generic;

import expression.generic.operation.Calculator;

public class DoubleCalculator implements Calculator<Double> {
    @Override
    public Double fromNumber(Number value) {
        return value.doubleValue();
    }

    @Override
    public Double fromString(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }
}