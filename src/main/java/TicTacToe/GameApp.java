package TicTacToe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameApp extends Application {
    private char currentPlayer;
    private char computerPlayer;
    private boolean isComputerTurn;
    String player = "your";
    Label gameInstructionsTitle = new Label("It's your turn.");
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(10); // Vertical layout with 10px spacing
        root.setStyle("-fx-alignment: center;"); // Center content

        // Create a label to describe or title the game
        Label gameTitle = new Label("Welcome to Tic Tac Toe!");
        gameTitle.setStyle("-fx-font-size: 18px;"); // Optional: set the font size


        // Create the "Start Game" button and set an event handler
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> {
            System.out.println("Start Game Button Clicked");
            try {
                choosePlayerView(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Add the button to the layout
        root.getChildren().addAll(gameTitle, startButton);

        // Create a scene and set it on the stage
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
        gameInstructionsTitle.setStyle("-fx-font-size: 18px;");
        GridPane gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        isComputerTurn = false;

        for (int row = 0; row < 4; row ++) {
            for (int col = 0; col < 4; col++) {
                Button cell = new Button();
                cell.setMinSize(50, 50);
                cell.setOnAction(event -> {
                    if (cell.getText().isEmpty()) {
                        fillCell(cell);
                    }
                });
                gameBoard.add(cell, row, col);
            }
        }

        VBox root = new VBox(10, gameInstructionsTitle, gameBoard);
        root.setAlignment(Pos.CENTER);

        Scene gameBoardScene = new Scene(root, 320, 240);
        stage.setScene(gameBoardScene);
        stage.show();
    }

    private void fillCell(Button cell) {
        System.out.println("Fill cell action user");
        cell.setText(String.valueOf(currentPlayer));
        // toggles between X and O player
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if (currentPlayer == computerPlayer) {
            gameInstructionsTitle.setText("It's computer's turn.");
        } else {
            gameInstructionsTitle.setText("It's your turn.");
        }
    }

    private void makeComputerTurn(Button cell) {
        System.out.println("Fill cell action computer");
        cell.setText(String.valueOf(computerPlayer));
    }


    public static void main(String[] args) {
        launch();
    }
}