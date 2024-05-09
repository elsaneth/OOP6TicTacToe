package org.example.oop6tictactore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApp extends Application {
    private char currentPlayer;
    private char computerPlayer;

    private Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApp.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Tic Tac Toe Game");
        stage.setScene(scene);
        stage.show();
    }

    public void choosePlayerView (Stage stage) throws IOException {
        this.currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GameApp.class.getResource("view.fxml"));
        Button chooseX = new Button("Play as X");
        Button chooseO = new Button("Play as O");

        // Set actions for choosing player
        chooseX.setOnAction(event -> {
            currentPlayer = 'X';
            computerPlayer = 'O';
        });

        chooseO.setOnAction(event -> {
            currentPlayer = 'O';
            computerPlayer = 'X';
        });

        // Create an HBox to hold the choice buttons
        HBox buttonBox = new HBox(20, chooseX, chooseO);
        buttonBox.setAlignment(Pos.CENTER);

        // VBox to hold label
        VBox vbox = new VBox(20, buttonBox);
        vbox.setAlignment(Pos.CENTER);

        Scene choosePlayerScene = new Scene(vbox, 320, 240);
        stage.setScene(choosePlayerScene);
        stage.show();

        GameController controller = fxmlLoader.getController();
        controller.setStage(currentStage);
    }

    public static void main(String[] args) {
        launch();
    }
}