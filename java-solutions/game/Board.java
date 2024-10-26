package game;

public interface Board {
    Position getPosition();

    Result makeMove(Move move);

    int getM();

    int getN();

    Cell[][] getField();

    void clearBoard();
}