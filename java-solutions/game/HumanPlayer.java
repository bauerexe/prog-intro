package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;

    public HumanPlayer() {
        this.out = System.out;
    }

    @Override
    public Move makeMove(Position position) {
        int m = position.getM();
        int n = position.getN();
        out.println("Current position:");
        out.println(position);
        Scanner sc = new Scanner(System.in);
        int row, col;
        Move move;

        while (true) {
            System.out.print("Enter row and column: ");

            if (!sc.hasNextLine()) {
                return null;
            }

            String line = sc.nextLine();

            try (Scanner lineScanner = new Scanner(line)) {
                row = lineScanner.nextInt() - 1;
                col = lineScanner.nextInt() - 1;
                move = new Move(row, col, position.getTurn());

                if (lineScanner.hasNext()) {
                    System.out.println("Incorrect input. Please enter two numbers.");
                    throw new InputMismatchException();
                }

                if (!(0 <= row && row < m) || !(0 <= col && col < n)) {
                    System.out.println("Row need be between 1 and " + m + ", col need be between 1 and " + n);
                    throw new IndexOutOfBoundsException();
                }

                if (!position.isValid(move)) {
                    System.out.println("Incorrect input. Сell is occupied.");
                    throw new IllegalArgumentException();
                }

                break;
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input. Please enter two numbers.");
            }
        }
        return move;
    }
}