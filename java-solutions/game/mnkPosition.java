package game;

import java.util.Map;

import static game.mnkBoard.getString;

public class mnkPosition implements Position {
    private final Cell turn;
    private final int n;
    private final int m;
    private final Cell[][] field;

    private final Map<Cell, Character> CELL_TO_SYMBOL = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.');

    public mnkPosition(Board board) {
        this.turn = board.getPosition().getTurn();
        this.n = board.getN();
        this.m = board.getM();
        this.field = board.getField();
    }

    @Override
    public Cell getTurn() {
        return this.turn;
    }

    @Override
    public boolean isValid(Move move) {
        int col = move.getCol();
        int row = move.getRow();
        return 0 <= row && row < m
                && 0 <= col && col < n
                && field[row][col] == Cell.E
                && move.getValue() == turn;
    }

    @Override
    public int getN() {
        return this.n;
    }

    @Override
    public int getM() {
        return this.m;
    }

    @Override
    public String toString() {
        return getString(n, m, field, CELL_TO_SYMBOL);
    }
}