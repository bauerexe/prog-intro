package expression.generic;

import expression.generic.operation.Calculator;
import expression.generic.operation.ExpressionParser;
import expression.generic.operation.TripleExpression;
import expression.parser.StringSource;


public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws Exception {
        Calculator<?> calculator = getCalculator(mode);
        return tabulateWithCalculator(calculator, expression, x1, x2, y1, y2, z1, z2);
    }

    private Calculator<?> getCalculator(String mode) {
        return switch (mode) {
            case "i" -> new IntCalculator();
            case "u" -> new UnsightIntCalculator();
            case "d" -> new DoubleCalculator();
            case "bi" -> new BigIntegerCalculator();
            case "b" -> new ByteCalculator();
            default -> throw new IllegalArgumentException("Unsupported mode: " + mode);
        };
    }

    private <T extends Number> Object[][][] tabulateWithCalculator(Calculator<T> calculator, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<T> expr = new ExpressionParser<>(new StringSource(expression), calculator);
        TripleExpression<T> triple = expr.parse(expression);
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        result[x - x1][y - y1][z - z1] = triple.evaluate(calculator.fromNumber(x), calculator.fromNumber(y), calculator.fromNumber(z), calculator);
                    } catch (Exception e) {
                        result[x - x1][y - y1][z - z1] = null;
                    }
                }
            }
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java Main -i/-d/-bi \"expression\"");
            return;
        }

        String mode = args[0].substring(1);
        String expression = args[1];

        GenericTabulator tabulator = new GenericTabulator();
        Object[][][] result = tabulator.tabulate(mode, expression, -2, 2, -2, 2, -2, 2);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                for (int k = 0; k < result[i][j].length; k++) {
                    System.out.printf("x=%d, y=%d, z=%d: %s%n", i - 2, j - 2, k - 2, result[i][j][k]);
                }
            }
        }

    }

}

