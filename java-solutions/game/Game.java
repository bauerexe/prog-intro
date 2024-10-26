package game;

public class Game {
    private boolean log;
    private final Player player1;
    private final Player player2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board, boolean log) {
        board.clearBoard();
        this.log = log;
        while (true) {
            int result = makeMove(board, player1, 1);
            if (result != -1) {
                return result;
            }
            result = makeMove(board, player2, 2);
            if (result != -1) {
                return result;
            }
        }
    }

    private int makeMove(Board board, Player player, int playerNum) {
        Move move;
        try {
            move = player.makeMove(new mnkPosition(board));
        } catch (RuntimeException e) {
            move = null;
        }

        Result res = board.makeMove(move);
        if (log) {
            System.out.println("Player " + playerNum + " Move:" + System.lineSeparator() + move + System.lineSeparator() + board);
            System.out.println("_".repeat(board.getN() * String.valueOf(board.getN()).length()));
        }

        return switch (res) {
            case WIN -> playerNum;
            case LOSE -> 3 - playerNum;
            case DRAW -> 0;
            default -> -1;
        };
    }
}
