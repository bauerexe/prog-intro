package expression.generic;

import expression.generic.operation.Calculator;

import java.math.BigInteger;

public class BigIntegerCalculator implements Calculator<BigInteger> {
    @Override
    public BigInteger fromNumber(Number value) {
        return new BigInteger(String.valueOf(value));
    }

    @Override
    public BigInteger fromString(String value) {
        return BigInteger.valueOf(Long.parseLong(value));
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }
}