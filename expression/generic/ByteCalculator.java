package expression.generic;

import expression.exceptions.OverflowException;
import expression.generic.operation.Calculator;

public class ByteCalculator implements Calculator<Byte> {

    @Override
    public Byte fromNumber(Number value) {
        return value.byteValue();
    }

    @Override
    public Byte fromString(String value) {
        return Byte.parseByte(value);
    }

    @Override
    public Byte add(Byte a, Byte b) {
        return (byte) (a + b);
    }


    @Override
    public Byte multiply(Byte a, Byte b) {

        return (byte) (a * b);
    }

    @Override
    public Byte divide(Byte a, Byte b) {
        return (byte) (a / b);
    }

    @Override
    public Byte negate(Byte a) {
        return (byte) -a;
    }

    @Override
    public Byte subtract(Byte a, Byte b){
        return (byte) (a - b);
    }
}
