package game;

import java.util.*;

import static java.lang.Math.max;

class mnkBoard implements Board, Position {
    private Cell[][] field;
    private Cell turn;
    private static int m, n, k;
    private int emptyCells;
    private final Map<Cell, Character> CELL_TO_SYMBOL = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.');

    public mnkBoard() {
        getMNK();
        this.field = new Cell[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(field[i], Cell.E);
        }
        this.turn = Cell.X;
        this.emptyCells = m * n;
    }

    private void getMNK() {
        final Scanner sc = new Scanner(System.in);
        boolean check = true;
        while (check) {
            System.out.print("Enter m, n, k: ");

            if (!sc.hasNextLine()) {
                System.out.println("Game ended by user.");
                System.exit(0);
            }

            String line = sc.nextLine();
            try (Scanner line_scanner = new Scanner(line)) {
                m = line_scanner.nextInt();
                n = line_scanner.nextInt();
                k = line_scanner.nextInt();
                if (line_scanner.hasNext()) {
                    throw new InputMismatchException();
                }
                if (k > max(n, m) || k <= 1 || m <= 0 || n <= 0) {
                    System.out.println("Game doesn't make sense with these parameters");
                    continue;
                }
                check = false;
            } catch (NoSuchElementException e) {
                System.out.println("Try again, you need to enter 3 numbers");
            }
        }
    }


    @Override
    public Result makeMove(Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        int row = move.getRow();
        int col = move.getCol();
        field[row][col] = move.getValue();
        emptyCells--;

        if (checkVector(row, col, turn, 0, 1) ||
                checkVector(row, col, turn, 1, 0) ||
                checkVector(row, col, turn, 1, 1) ||
                checkVector(row, col, turn, -1, 1)) {
            return Result.WIN;
        }

        if (emptyCells == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private boolean checkVector(int row, int col, Cell value, int dx, int dy) {
        int count = 0;
        for (int i = -k + 1; i < k; i++) {
            int r = row + i * dx;
            int c = col + i * dy;

            if (r >= 0 && r < m && c >= 0 && c < n) {
                if (field[r][c] == value) {
                    count++;
                    if (count == k) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isValid(Move move) {
        if (move == null){
            return false;
        }
        int row = move.getRow();
        int col = move.getCol();
        return row >= 0 && row < m &&
                col >= 0 && col < n &&
                field[row][col] == Cell.E &&
                move.getValue() == turn;
    }


    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public Cell[][] getField() {
        return this.field;
    }

    @Override
    public void clearBoard() {
        this.field = new Cell[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(field[i], Cell.E);
        }
        this.turn = Cell.X;
        this.emptyCells = n * m;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public String toString() {
        return getString(n, m, field, CELL_TO_SYMBOL);
    }

    static String getString(int n, int m, Cell[][] field, Map<Cell, Character> cellToSymbol) {
        int maxSymbolLength = String.valueOf(n).length();
        StringBuilder sb = new StringBuilder(" ".repeat(maxSymbolLength + 1) + "|");
        sb.append("1");
        for (int i = 1; i < n; i++) {
            sb.append(String.format("%" + (maxSymbolLength + 1) + "d", i + 1));
        }
        sb.append("\n");
        for (int i = 0; i < m; i++) {
            sb.append(String.format("%" + (maxSymbolLength + 1) + "d", i + 1)).append("|");
            for (Cell cell : field[i]) {
                String symbol = String.valueOf(cellToSymbol.get(cell));
                int spacesToAdd = maxSymbolLength - symbol.length() + 1;
                sb.append(symbol).append(" ".repeat(spacesToAdd));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
