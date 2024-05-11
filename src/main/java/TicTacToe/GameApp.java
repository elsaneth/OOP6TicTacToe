package TicTacToe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class GameApp extends Application {
    private char currentPlayer;
    private char computerPlayer;
    private Label whosTurnTitle;
    private String player;
    private GridPane gameBoard = new GridPane();

    private Button[][] gameBoardArray = null;
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10);
        root.setStyle("-fx-alignment: center;");

        Label gameTitle = new Label("Welcome to Tic Tac Toe!");
        gameTitle.setStyle("-fx-font-size: 18px;");


        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> {
            System.out.println("Start Game Button Clicked");
            try {
                choosePlayerView(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        root.getChildren().addAll(gameTitle, startButton);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Tic Tac Toe Game");
        stage.setScene(scene);
        stage.show();
    }

    public void choosePlayerView (Stage stage) throws IOException {
        Label instructionsTitle = new Label("Choose player: ");
        instructionsTitle.setStyle("-fx-font-size: 18px;");
        Button chooseX = new Button("Player X");
        Button chooseO = new Button("Player O");

        chooseX.setOnAction(event -> {
            System.out.println("Player X button is clicked");
            try {
                gameBoardView(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentPlayer = 'X';
            computerPlayer = 'O';
        });

        chooseO.setOnAction(event -> {
            System.out.println("Player O button is clicked");
            try {
                gameBoardView(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentPlayer = 'O';
            computerPlayer = 'X';
        });

        HBox buttonBox = new HBox(20, chooseX, chooseO);
        buttonBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, instructionsTitle, buttonBox);
        root.setAlignment(Pos.CENTER);

        Scene choosePlayerScene = new Scene(root, 320, 240);
        stage.setScene(choosePlayerScene);
        stage.show();
    }

    public void gameBoardView(Stage stage) throws IOException {
        whosTurnTitle = new Label("It's your turn.");
        whosTurnTitle.setStyle("-fx-font-size: 18px;");
        gameBoard.setAlignment(Pos.CENTER);

        for (int row = 0; row < 4; row ++) {
            for (int col = 0; col < 4; col++) {
                Button cell = new Button();
                cell.setMinSize(50, 50);
                cell.setOnAction(event -> {
                    if (cell.getText().isEmpty()) {
                        if (currentPlayer != computerPlayer) {
                            try {
                                fillCell(cell);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                makeComputerTurn(cell);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                gameBoard.add(cell, row, col);
            }
        }

        VBox root = new VBox(10, whosTurnTitle, gameBoard);
        root.setAlignment(Pos.CENTER);

        Scene gameBoardScene = new Scene(root, 320, 240);
        stage.setScene(gameBoardScene);
        stage.show();
    }

    private void fillCell(Button cell) throws IOException {
        System.out.println("Fill cell action user");
        cell.setText(String.valueOf(currentPlayer));
        if (isWinner()) {
            showWinAlert();
        }
        // toggles between X and O player
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if (currentPlayer == computerPlayer) {
            whosTurnTitle.setText("It's computer's turn.");
        } else {
            whosTurnTitle.setText("It's your turn.");
        }
    }

    private void makeComputerTurn(Button cell) throws IOException {
        System.out.println("Fill cell action computer");
        cell.setText(String.valueOf(currentPlayer));
        if (isWinner()) {
            showWinAlert();
        }
        // toggles between X and O player
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if (currentPlayer == computerPlayer) {
            whosTurnTitle.setText("It's computer's turn.");
        } else {
            whosTurnTitle.setText("It's your turn.");
        }
    }

    private boolean isWinner() {
        initializeGridPaneArray();
        boolean isWinner = false;
        player = "You";

        for (int i = 0; i < 4; i++) {
            // check rows
            if (gameBoardArray[i][0].getText().equals(gameBoardArray[i][1].getText()) &&
                    gameBoardArray[i][1].getText().equals(gameBoardArray[i][2].getText()) &&
                    gameBoardArray[i][2].getText().equals(gameBoardArray[i][3].getText()) &&
                    !gameBoardArray[i][0].getText().isEmpty()) {
                isWinner = true;
                if (currentPlayer == computerPlayer) {
                    player = "Computer";
                }
                System.out.println(player + " won");
                return isWinner;
            }
            // check columns
            if (gameBoardArray[0][i].getText().equals(gameBoardArray[1][i].getText()) &&
                    gameBoardArray[1][i].getText().equals(gameBoardArray[2][i].getText()) &&
                    gameBoardArray[2][i].getText().equals(gameBoardArray[3][i].getText()) &&
                    !gameBoardArray[0][i].getText().isEmpty()) {
                isWinner = true;
                if (currentPlayer == computerPlayer) {
                    player = "Computer";
                }
                System.out.println(player + " won");
                return isWinner;
            }
        }

        // check diagonals top-left to bottom-right
        if (gameBoardArray[0][0].getText().equals(gameBoardArray[1][1].getText()) &&
                gameBoardArray[1][1].getText().equals(gameBoardArray[2][2].getText()) &&
                gameBoardArray[2][2].getText().equals(gameBoardArray[3][3].getText()) &&
                !gameBoardArray[0][0].getText().isEmpty()) {
            isWinner = true;
            if (currentPlayer == computerPlayer) {
                player = "Computer";
            }
            System.out.println(player + " won");
            return isWinner;
        }

        // check diagonals bottom-left to top-right
        if (gameBoardArray[0][3].getText().equals(gameBoardArray[1][2].getText()) &&
                gameBoardArray[1][2].getText().equals(gameBoardArray[2][1].getText()) &&
                gameBoardArray[2][1].getText().equals(gameBoardArray[3][0].getText()) &&
                !gameBoardArray[0][3].getText().isEmpty()) {
            isWinner = true;
            if (currentPlayer == computerPlayer) {
                player = "Computer";
            }
            System.out.println(player + " won");
            return isWinner;
        }
        return isWinner;
    }

    private void initializeGridPaneArray() {
        this.gameBoardArray = new Button[4][4];
        for (Node button : this.gameBoard.getChildren()) {
            this.gameBoardArray[GridPane.getRowIndex(button)][GridPane.getColumnIndex(button)] = (Button) button;
        }
    }

    public void showWinAlert() throws IOException {
        Alert winAlert = new Alert(Alert.AlertType.CONFIRMATION);
        winAlert.setTitle("Game Over");
        winAlert.setHeaderText(player + " won.");
        winAlert.setContentText("Would you like to start a new game?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        winAlert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = winAlert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == noButton) {
                Platform.exit();
            }
            else {
                System.out.println("Yes button is clicked");
                Stage newGame = new Stage();
                start(newGame);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}