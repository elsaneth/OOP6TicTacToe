package org.example.oop6tictactore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {
    public Label welcomeText;
    private  Stage stage;

    @FXML
    protected void onStartGameButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("choosePlayerView.fxml"));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root, 320, 240);

            stage.setScene(newScene); // Change the scene on the stage
            stage.show(); // Display the new scene
        } catch (IOException e) {
            e.printStackTrace(); // Handle any exceptions during scene loading
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage; // Set the stage reference
    }
}