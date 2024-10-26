package expression;

public class Main {
    public static void main(String[] args) {
        TripleExpression exp = new Subtract(
                new Multiply(
                        new Variable("z"),
                        new Variable("x")
                ),
                new Multiply(
                        new Const(2),
                        new Variable("y")
                )
        );
        System.out.println(exp);
        int x = 1;
        int y = 2;
        int z = 3;
        System.out.println(exp.evaluate(x, y, z));

    }
}
