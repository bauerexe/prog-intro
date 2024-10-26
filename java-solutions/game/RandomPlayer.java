package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private Random random = new Random();

    @Override
    public Move makeMove(Position position) {
        int m = position.getM();
        int n = position.getN();
        while (true) {
            int row = random.nextInt(m);
            int col = random.nextInt(n);
            Move move = new Move(row, col, position.getTurn());
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}