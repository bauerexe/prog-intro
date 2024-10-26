package game;

public class Main {
    public static void main(String[] args) {
        Board board = new mnkBoard();
        System.out.println(new Olympiad(false).play(board));
    }
}