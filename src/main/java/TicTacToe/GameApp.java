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
import java.util.*;
import java.util.stream.Collectors;

public class GameApp extends Application {
    private char currentPlayer;
    private char computerPlayer;
    private char userPlayer;
    private Label whosTurnTitle;
    private String player;
    private GridPane gameBoard = new GridPane();
    private Button[][] gameBoardArray = null;
    Boolean isFourthAdded = false;
    Boolean isAdded = false;
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
                currentPlayer = 'X';
                userPlayer = currentPlayer;
                computerPlayer = 'O';
                gameBoardView(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        chooseO.setOnAction(event -> {
            System.out.println("Player O button is clicked");
            try {
                currentPlayer = 'O';
                userPlayer = currentPlayer;
                computerPlayer = 'X';
                gameBoardView(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        whosTurnTitle = new Label("It's your turn: ");
        whosTurnTitle.setStyle("-fx-font-size: 18px;");
        gameBoard.setAlignment(Pos.CENTER);

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Button cell = new Button();
                cell.setMinSize(50, 50);
                cell.setOnAction(event ->  {
                    if (cell.getText().isEmpty()) {
                        try {
                            fillCell(cell);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
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
        System.out.println("Current player: " + currentPlayer);
        if (currentPlayer != computerPlayer) {
            System.out.println("Fill cell action user");
            cell.setText(String.valueOf(currentPlayer));
        }
        if (isWinner() || isFull()) {
            showGameOverAlert();
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            makeComputerTurn();
        }
    }

    private void makeComputerTurn() throws IOException {
        System.out.println("Fill cell action computer");
        setFourthSameMark(currentPlayer);
        if (isWinner() || isFull()) {
            showGameOverAlert();
        }
        if (!isFourthAdded) {
            setFourthSameMark(userPlayer);
            if (isWinner() || isFull()) {
                showGameOverAlert();
            }
        }
        if (!isFourthAdded) {
            addSecondOrThird();
            if (isWinner() || isFull()) {
                showGameOverAlert();
            }
        }
        if (!isFourthAdded && !isAdded) {
            Random random = new Random();
            int row = random.nextInt(4);
            int col = random.nextInt(4);
            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
            while (!button.getText().isEmpty()) {
                row = random.nextInt(4);
                col = random.nextInt(4);
                button = (Button) gameBoard.getChildren().get(row * 4 + col);
            }
            button.setText(String.valueOf(currentPlayer));
            if (isWinner() || isFull()) {
                showGameOverAlert();
            }
        }
        // toggles between X and O player
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean isWinner() {
        initializeGameboardArray();
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
                whosTurnTitle.setText(player + " won");
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
                whosTurnTitle.setText(player + " won");
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
            whosTurnTitle.setText(player + " won");
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
            whosTurnTitle.setText(player + " won");
            System.out.println(player + " won");
            return isWinner;
        }
        return isWinner;
    }

    public boolean isFull() {
        initializeGameboardArray();
        boolean result = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoardArray[i][j].getText().isEmpty()) {
                    result = false;
                    return result;
                }
            }
        }
        return result;
    }

    private void initializeGameboardArray() {
        this.gameBoardArray = new Button[4][4];
        for (Node button : this.gameBoard.getChildren()) {
            this.gameBoardArray[GridPane.getRowIndex(button)][GridPane.getColumnIndex(button)] = (Button) button;
        }
    }

    public void showGameOverAlert() throws IOException {
        Alert winAlert = new Alert(Alert.AlertType.CONFIRMATION);
        winAlert.setTitle("Game Over");
        if (isFull()) {
            if (isWinner()) {
                winAlert.setHeaderText(player + " won.");
            } else {
                winAlert.setHeaderText("Boxes full. Nobody won.");
                whosTurnTitle.setText("Nobody won!");
            }
        } else {
            winAlert.setHeaderText(player + " won.");
        }
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
                Stage currentStage = (Stage) gameBoard.getScene().getWindow();
                currentStage.close();

                System.out.println("Yes button is clicked");
                Stage newGame = new Stage();
                gameBoard = new GridPane();
                start(newGame);
            }
        }
    }

    public void setFourthSameMark(char player) {
        isFourthAdded = false;
        int row;
        int col;
        Button a;
        Button b;
        Button c;
        Button d;

        // columns
        for (int i = 0; i < 3; i++) {
            row = i;
            a = gameBoardArray[0][i];
            b = gameBoardArray[1][i];
            c = gameBoardArray[2][i];
            d = gameBoardArray[3][i];
            List<Button> buttonColumn = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtons = buttonColumn.stream().filter(button -> !button.getText().isEmpty()).toList();
            if (filledButtons.size() == 3) {
                if (Objects.equals(filledButtons.get(0).getText(), filledButtons.get(1).getText()) &&
                        Objects.equals(filledButtons.get(1).getText(), filledButtons.get(2).getText()) &&
                        filledButtons.get(0).getText().charAt(0) == player) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonColumn.get(j).getText().isEmpty()) {
                            col = j;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding 4th mark: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isFourthAdded = true;
                            break;
                        }
                    }
                }
            }
        }

        // rows
        for (int i = 0; i < 3; i++) {
            col = i;
            a = gameBoardArray[i][0];
            b = gameBoardArray[i][1];
            c = gameBoardArray[i][2];
            d = gameBoardArray[i][3];
            List<Button> buttonRow = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtons = new ArrayList<>();
            for (Button button : buttonRow) {
                if (!button.getText().isEmpty()) {
                    filledButtons.add(button);
                }
            }
            if (filledButtons.size() == 3) {
                if (Objects.equals(filledButtons.get(0).getText(), filledButtons.get(1).getText()) &&
                        Objects.equals(filledButtons.get(1).getText(), filledButtons.get(2).getText()) &&
                        filledButtons.get(0).getText().charAt(0) == player) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonRow.get(j).getText().isEmpty()) {
                            row = j;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding 4th mark: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isFourthAdded = true;
                            break;
                        }
                    }
                }
            }
        }

        // check diagonals top-left to bottom-right
        if (!isFourthAdded) {
            a = gameBoardArray[0][0];
            b = gameBoardArray[1][1];
            c = gameBoardArray[2][2];
            d = gameBoardArray[3][3];
            List<Button> buttonDiagonal1 = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtonsD1 = new ArrayList<>();
            for (Button button : buttonDiagonal1) {
                if (!button.getText().isEmpty()) {
                    filledButtonsD1.add(button);
                }
            }
            if (filledButtonsD1.size() == 3) {
                if (Objects.equals(filledButtonsD1.get(0).getText(), filledButtonsD1.get(1).getText()) &&
                        Objects.equals(filledButtonsD1.get(1).getText(), filledButtonsD1.get(2).getText()) &&
                        filledButtonsD1.get(0).getText().charAt(0) == player) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonDiagonal1.get(j).getText().isEmpty()) {
                            row = j;
                            col = row;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding 4th mark: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isFourthAdded = true;
                            break;
                        }
                    }
                }
            }
        }

        // check diagonals bottom-right to top-left
        if (!isFourthAdded) {
            a = gameBoardArray[3][0];
            b = gameBoardArray[2][1];
            c = gameBoardArray[1][2];
            d = gameBoardArray[0][3];
            List<Button> buttonDiagonal2 = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtonsD2 = new ArrayList<>();
            for (Button button : buttonDiagonal2) {
                if (!button.getText().isEmpty()) {
                    filledButtonsD2.add(button);
                }
            }
            if (filledButtonsD2.size() == 3) {
                if (Objects.equals(filledButtonsD2.get(0).getText(), filledButtonsD2.get(1).getText()) &&
                        Objects.equals(filledButtonsD2.get(1).getText(), filledButtonsD2.get(2).getText()) &&
                        filledButtonsD2.get(0).getText().charAt(0) == player) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonDiagonal2.get(j).getText().isEmpty()) {
                            row = j;
                            col = (row - 3) * -1;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding 4th mark: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isFourthAdded = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void addSecondOrThird() {
        isAdded = false;
        int row;
        int col;
        Button a;
        Button b;
        Button c;
        Button d;

        // columns
        for (int i = 0; i < 3; i++) {
            row = i;
            a = gameBoardArray[0][i];
            b = gameBoardArray[1][i];
            c = gameBoardArray[2][i];
            d = gameBoardArray[3][i];
            List<Button> buttonColumn = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtons = buttonColumn.stream().filter(button -> !button.getText().isEmpty()).toList();
            if (!filledButtons.isEmpty() && filledButtons.stream().map(Button::getText).distinct().count() == 1) {
                if (filledButtons.getFirst().getText().charAt(0) == currentPlayer) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonColumn.get(j).getText().isEmpty()) {
                            col = j;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding calculated mark to column: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isAdded = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!isAdded) {
            // rows
            for (int i = 0; i < 3; i++) {
                col = i;
                a = gameBoardArray[i][0];
                b = gameBoardArray[i][1];
                c = gameBoardArray[i][2];
                d = gameBoardArray[i][3];
                List<Button> buttonColumn = new ArrayList<>(Arrays.asList(a, b, c, d));
                List<Button> filledButtons = buttonColumn.stream().filter(button -> !button.getText().isEmpty()).toList();
                if (!filledButtons.isEmpty() && filledButtons.stream().map(Button::getText).distinct().count() == 1) {
                    if (filledButtons.getFirst().getText().charAt(0) == currentPlayer) {
                        for (int j = 0; j < 4; j++) {
                            if (buttonColumn.get(j).getText().isEmpty()) {
                                row = j;
                                Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                                System.out.println("Adding calculated mark to row: ");
                                System.out.println("Row: " + row + " Col: " + col);
                                button.setText(String.valueOf(currentPlayer));
                                isAdded = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (!isAdded) {
            // check diagonals top-left to bottom-right
            a = gameBoardArray[0][0];
            b = gameBoardArray[1][1];
            c = gameBoardArray[2][2];
            d = gameBoardArray[3][3];
            List<Button> buttonDiagonal1 = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtonsD1 = new ArrayList<>();
            for (Button button : buttonDiagonal1) {
                if (!button.getText().isEmpty()) {
                    filledButtonsD1.add(button);
                }
            }
            if (filledButtonsD1.stream().map(Button::getText).distinct().count() == 1) {
                if (filledButtonsD1.getFirst().getText().charAt(0) == currentPlayer) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonDiagonal1.get(j).getText().isEmpty()) {
                            row = j;
                            col = row;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding calculated diagaonal 1 mark: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isFourthAdded = true;
                            break;
                        }
                    }
                }
            }

        }

        if (!isAdded) {
            // check diagonals bottom-right to top-left
            a = gameBoardArray[3][0];
            b = gameBoardArray[2][1];
            c = gameBoardArray[1][2];
            d = gameBoardArray[0][3];
            List<Button> buttonDiagonal2 = new ArrayList<>(Arrays.asList(a, b, c, d));
            List<Button> filledButtonsD2 = new ArrayList<>();
            for (Button button : buttonDiagonal2) {
                if (!button.getText().isEmpty()) {
                    filledButtonsD2.add(button);
                }
            }
            if (filledButtonsD2.stream().map(Button::getText).distinct().count() == 1) {
                if (filledButtonsD2.getFirst().getText().charAt(0) == currentPlayer) {
                    for (int j = 0; j < 4; j++) {
                        if (buttonDiagonal2.get(j).getText().isEmpty()) {
                            row = j;
                            col = (row - 3) * -1;
                            Button button = (Button) gameBoard.getChildren().get(row * 4 + col);
                            System.out.println("Adding calculated diagaonal 2 mark: ");
                            System.out.println("Row: " + row + " Col: " + col);
                            button.setText(String.valueOf(currentPlayer));
                            isFourthAdded = true;
                            break;
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        launch();
    }
}