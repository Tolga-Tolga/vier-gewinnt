import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


 public class VierGewinnt {
    private PlayingField playingField;
    private BufferedReader reader;
    private String playerInput;
    private int playerTurn;
    private boolean gameOver = false;
    private boolean noOneWon = false;
    public static void main(String[] args) {
        VierGewinnt gameController = new VierGewinnt();
        gameController.create();    
        gameController.run();
    }

    private void create() {
        this.playerTurn = Config.PLAYER_TURN;
        if (playerTurn < 1 || playerTurn > 2) {
            System.out.println("Config player turn error!");
            return;
        }
        if (Config.PLAYFIELD_SIZE_X < 4) {
            System.out.println("Config playfield size error!");
            return;
        }
        if (Config.PLAYFIELD_SIZE_Y < 4) {
            System.out.println("Config playfield size error!");
            return;
        }
        this.playingField = new PlayingField(Config.PLAYFIELD_SIZE_X, Config.PLAYFIELD_SIZE_Y);
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Spieler 1 ist: " + Config.PLAYER_1_SYMBOL + "\nSpieler 2 ist: " + Config.PLAYER_2_SYMBOL + "\n");
        System.out.println("Spieler " + playerTurn + " am Zug!");
        System.out.print("Eingabe: ");
    }

    private void run() {
        while (true) {
            try {
                if (reader.ready()) {
                    turn();
                    if (gameOver) {
                        if (noOneWon) {
                            System.out.println("Keiner hat gewonnen!");
                        }
                        else{
                           System.out.println("Spieler " + playerTurn + " hat gewonnen!"); 
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Gib eine Zahl ein!");
            }
        }
    }

    private void turn() throws IOException {
        playerInput = reader.readLine();
        int num = Integer.parseInt(playerInput);
        int ret = playingField.insertStone(num, playerTurn);
        switch (ret) {
            case 0:
                playingField.drawField();
                if (!playingField.fieldIsFull()) {
                    gameOver = true;
                    noOneWon = true;
                    return;
                }
                if (playingField.hasPlayer4Chain(playerTurn)) {
                    gameOver = true;
                    return;
                }
                switchTurn();
                System.out.println("Spieler " + playerTurn + " am Zug!");
                System.out.print("Eingabe: ");
                break;
            case 1:
                System.out.println("Gib ein freies Feld an!");
                break;
            case 2:
                System.out.println("Illegaler Input!");
                break;
            default:
                break;
        }
    }

    private void switchTurn() {
        if (playerTurn == 1) {
            playerTurn = 2;
        }
        else playerTurn = 1;
    }
 }