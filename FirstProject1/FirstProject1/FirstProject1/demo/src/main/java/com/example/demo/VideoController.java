package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoController implements Initializable {
    @FXML
    private Button pauseButton; // fx:id кнопки Pause
    @FXML
    private MediaView mediaView;  // fx:id контролу mediaView
    @FXML
    private Button playButton; // fx:id кнопки Play
    @FXML
    private Button resetButton; // fx:id кнопки Reset
    private Stage newStage;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    @FXML
    private Button btnAudio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        file = new File("C:\\Users\\HP\\Downloads\\FirstProject1\\FirstProject1\\video.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        btnAudio.setOnAction(event -> nextPr(event));



    }
    @FXML
    void playButton_method(ActionEvent event) { // реалізація кнопки Play
        mediaPlayer.play();
    }
    @FXML
    void pauseButton_method(ActionEvent event) { // реалізація кнопки Pause
        mediaPlayer.pause();
    }
    @FXML
    void resetButton_method(ActionEvent event) { // реалізація кнопки Reset
        if(mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
            mediaPlayer.play();
        }
    }

    @FXML
    public void nextPr(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("audio.fxml"));
            Parent root = loader.load();
            AudioController audioController = loader.getController();

            Stage otherDialogStage = new Stage();
            otherDialogStage.setTitle("Пр7");
            otherDialogStage.initModality(Modality.WINDOW_MODAL);
            otherDialogStage.initOwner(btnAudio.getScene().getWindow());
            Scene scene = new Scene(root);
            otherDialogStage.setScene(scene);

            audioController.setNewStage(otherDialogStage);

            otherDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setNewStage(Stage newStage) {
        this.newStage = newStage;
    }

}
