package game;

import java.util.*;

class Olympiad {
    private static final int INITIAL_POWER_OF_TWO = 2;

    private final boolean log;
    private List<Player> players;
    private Set<Integer> playersInGame;
    private final Set<Integer> playersWin;
    private final Random random;
    private final Map<String, List<String>> playersGrid;
    private final Map<Integer, List<Integer>> playersPlace;


    public Olympiad(boolean log) {
        this.log = log;
        this.playersInGame = new HashSet<>();
        this.playersPlace = new HashMap<>();
        this.playersWin = new HashSet<>();
        this.random = new Random();
        this.playersGrid = new LinkedHashMap<>();
        fillPlayers();
    }

    public void fillPlayers() {
        int botPlayers = getInput("Enter number of bot players: ");
        int humanPlayers = getInput("Enter number of real players: ");
        while (botPlayers + humanPlayers <= 1 || botPlayers < 0 || humanPlayers < 0) {
            System.out.println("Game doesn't make sense with these parameters: nums of players <= 1 or num < 0");
            botPlayers = getInput("Enter number of bot players: ");
            humanPlayers = getInput("Enter number of real players: ");
        }
        players = new ArrayList<>();
        for (int i = 0; i < botPlayers; i++) {
            players.add(new RandomPlayer());
        }
        for (int i = 0; i < humanPlayers; i++) {
            players.add(new HumanPlayer());
        }
    }

    private int getInput(String string) {
        final Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        int num = 0;
        while (!validInput) {
            System.out.print(string);
            if (!sc.hasNextLine()) {
                System.out.println("Game ended by user.");
                System.exit(0);
            }

            String line = sc.nextLine();
            try (Scanner lineScanner = new Scanner(line)) {
                num = lineScanner.nextInt();
                if (lineScanner.hasNext()) {
                    throw new InputMismatchException();
                }
                validInput = true;
            } catch (NoSuchElementException e) {
                System.out.println("Try again, you need to enter one number");
            }
        }
        return num;
    }

    public String play(Board board) {
        initializePlayersInGame();

        while (playersInGame.size() != 1) {
            playRound(board);
        }
        System.out.println("Game Grid:");
        System.out.println(printBracket());
        System.out.println("-".repeat(board.getN() * 4));
        System.out.println("Game Result:");
        return tournamentTable() + System.lineSeparator() + "Tournament win: Player " + (playersPlace.get(1).get(0) + 1);
    }

    private void initializePlayersInGame() {
        playersInGame.clear();
        for (int i = 0; i < players.size(); i++) {
            playersInGame.add(i);
        }
    }

    private void playRound(Board board) {
        int powerOfTwo = calculatePowerOfTwo();
        int power = calculateLog(powerOfTwo);
        int powerOfTwoDown = powerOfTwo / 2;

        printRoundInfo(board, powerOfTwoDown);

        int numRounds = playersInGame.size() - powerOfTwoDown;
        playersToNextRound(numRounds);

        for (int i = 0; i < numRounds; i++) {
            int playerIndex1 = getRandomPlayerIndex();
            int playerIndex2 = getRandomPlayerIndex();

            playSingleGame(board, playerIndex1, playerIndex2, power + 1, powerOfTwoDown);
        }

        updatePlayersInGame();
    }

    private int calculatePowerOfTwo() {
        int powerOfTwo = INITIAL_POWER_OF_TWO;
        while (playersInGame.size() > powerOfTwo) {
            powerOfTwo *= 2;
        }
        return powerOfTwo;
    }

    private int calculateLog(int powerOfTwo) {
        return (int) (Math.log(powerOfTwo) / Math.log(2));
    }

    private void printRoundInfo(Board board, int powerOfTwoDown) {
        System.out.println("-".repeat(board.getN() * 4));
        if (powerOfTwoDown != 1) {
            System.out.println("Tournament 1/" + powerOfTwoDown);
        } else {
            System.out.println("Tournament final");
        }
    }

    private void playersToNextRound(int numRounds) {
        if (playersInGame.size() != numRounds * 2) {
            System.out.println(System.lineSeparator() + "Advance to the next part of the tournament:");
        }
        while (playersInGame.size() != numRounds * 2) {
            int playerIndex1 = getRandomPlayerIndex();
            System.out.print("Player " + (playerIndex1 + 1) + System.lineSeparator());
            playersWin.add(playerIndex1);
        }
    }

    private void playSingleGame(Board board, int playerIndex1, int playerIndex2, int power, int powerOfTwoDown) {
        Game game = new Game(players.get(playerIndex1), players.get(playerIndex2));
        System.out.println("Player " + (playerIndex1 + 1) + "(X)" + " vs Player " + (playerIndex2 + 1) + "(O)");
        int result = game.play(board, log);

        while (result == 0) {
            result = game.play(board, log);
        }

        if (result == 1) {
            updateResult(playerIndex1, playerIndex2, power, powerOfTwoDown);
            System.out.println(formatGameResult(board, playerIndex1));
        } else if (result == 2) {
            updateResult(playerIndex2, playerIndex1, power, powerOfTwoDown);
            System.out.println(formatGameResult(board, playerIndex2));
        }
    }

    private void updatePlayersInGame() {
        playersInGame = new HashSet<>(playersWin);
        playersWin.clear();
    }

    private void updateResult(int indexWin, int indexLose, int place, int powerOfTwoDown) {
        playersWin.add(indexWin);

        String part = (powerOfTwoDown == 1) ? "Final" : "1/" + powerOfTwoDown;
        int numWin = indexWin + 1;
        int numLose = indexLose + 1;

        playersGrid.computeIfAbsent(part, k -> new ArrayList<>()).add(numWin + " vs " + numLose + " -> " + numWin);
        playersPlace.computeIfAbsent(place, k -> new ArrayList<>()).add(numLose);

        if (place == 2) {
            playersPlace.computeIfAbsent(1, k -> new ArrayList<>()).add(numWin);
        }
    }


    private int getRandomPlayerIndex() {
        int randomIndex = random.nextInt(playersInGame.size());
        Iterator<Integer> iterator = playersInGame.iterator();
        int playerIndex1 = 0;
        for (int i = 0; i <= randomIndex; i++) {
            playerIndex1 = iterator.next();
        }
        iterator.remove();
        return playerIndex1;
    }

    private String formatGameResult(Board board, int winnerIndex) {
        int winningPlayer = winnerIndex + 1;

        return "Result: Player " + winningPlayer + " wins" +
                System.lineSeparator() +
                board;
    }
    private String buildBracketString(Map<?, ?> bracketMap, String title) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : bracketMap.entrySet()) {
            sb.append(title).append(entry.getKey()).append(":");
            List<?> list = (List<?>) entry.getValue();
            for (Object element : list) {
                sb.append(System.lineSeparator()).append("\t\t\t\t\tPlayer ").append(element.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public String tournamentTable() {
        return buildBracketString(playersPlace, "Tournament place ");
    }

    public String printBracket() {
        return buildBracketString(playersGrid, "Part Tournament ");
    }
}
