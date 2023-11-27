package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonsController {

    @FXML
    private Button answer;

    @FXML
    private CheckBox checkBox1;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private CheckBox checkBox3;

    @FXML
    private CheckBox checkBox4;

    @FXML
    private ComboBox comboBox;
    @FXML
    private Label lblAnswerCombo;

    @FXML
    private Label lblTrueAnswer;

    @FXML
    private Label lblAnswerChoice;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private RadioButton radioLay;

    @FXML
    private RadioButton radioCode;

    @FXML
    private RadioButton radioHier;

    @FXML
    private RadioButton radioProp;

    @FXML
    private Label lblRadio;

    private ToggleGroup radiotoggleGroup;

    @FXML
    private Button btnNextTasks;

    @FXML
    private Button btnNextPrs;

    private Stage dialogStage;

    private HelloController helloController;

    public void initialize(){
        lblTrueAnswer.setText("");
        lblAnswerChoice.setText("");
        choiceBox.getItems().addAll("Правильно","Неправильно");
        lblAnswerCombo.setText("");
        comboBox.getItems().addAll("BorderPane", "AncorePane","VBox", "HBox");
        lblRadio.setText("");
        radiotoggleGroup = new ToggleGroup();
        this.radioProp.setToggleGroup(radiotoggleGroup);
        this.radioLay.setToggleGroup(radiotoggleGroup);
        this.radioHier.setToggleGroup(radiotoggleGroup);
        this.radioCode.setToggleGroup(radiotoggleGroup);

        btnNextTasks.setOnAction(event -> nextTasks(event));
        btnNextPrs.setOnAction(event -> nextPr(event));
    }

    @FXML
    public void checkBoxAnswer(ActionEvent event) {
        String answer = "Ваша відповідь:";

        if(checkBox1.isSelected() & checkBox2.isSelected() & checkBox3.isSelected() & !checkBox4.isSelected()){
            answer += "\nпогодженість" + "\nдружність" + "\nгнучкість" + "\nВаша відповідь вірна!!!";
            this.lblTrueAnswer.setText(answer);
        } else {
            this.lblTrueAnswer.setText("Відповідь не вірна!");
        }

    }

    @FXML
    public void choiceBoxAnswer(ActionEvent event) {
        String trueAnswer = "Неправильно";

        if (choiceBox.getValue().toString().equals(trueAnswer)){
            lblAnswerChoice.setText("Ваша відповідь: " + choiceBox.getValue().toString() + "\nВідповідь вірна!");
        } else {
            lblAnswerChoice.setText("Ваша відповідь: " + choiceBox.getValue().toString() + "\nВідповідь не вірна!");
        }
    }

    @FXML
    public void comboBoxPressed(ActionEvent event) {
        String trueAnswer = "BorderPane";
        if (comboBox.getValue().toString().equals(trueAnswer)) {
            lblAnswerCombo.setText("Відповідь: " + comboBox.getValue().toString() + "\nВаша відповідь вірна!");
        } else {
            lblAnswerCombo.setText("Відповідь: " + comboBox.getValue().toString() + "\nВаша відповідь не вірна!");
        }
    }

    @FXML
    public void radioAnswer(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) radiotoggleGroup.getSelectedToggle();
        String answer = selectedRadioButton.getText();
        String trueAnswer = "Properties";

        if (answer.equals(trueAnswer)){
            lblRadio.setText("Ваша відповідь: " + answer + "\nВаша відповідь вірна!");
        } else {
            lblRadio.setText("Ваша відповідь: " + answer + "\nВаша відповідь не вірна!");
        }
    }

    @FXML
    public void nextTasks(ActionEvent event ) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controllersButtonsTask2.fxml"));
            Parent root = loader.load();
            ButtonsControllerTask2 buttonsControllerTask2 = loader.getController();

            Stage otherDialogStage = new Stage();
            otherDialogStage.setTitle("Практична робота №6");
            otherDialogStage.initModality(Modality.WINDOW_MODAL);
            otherDialogStage.initOwner(btnNextTasks.getScene().getWindow());
            Scene scene = new Scene(root);
            otherDialogStage.setScene(scene);

            buttonsControllerTask2.setNewStage(otherDialogStage);

            otherDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void nextPr(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("video.fxml"));
            Parent root = loader.load();
            VideoController videoController = loader.getController();

            Stage otherDialogStage = new Stage();
            otherDialogStage.setTitle("ПР7");
            otherDialogStage.initModality(Modality.WINDOW_MODAL);
            otherDialogStage.initOwner(btnNextPrs.getScene().getWindow());
            Scene scene = new Scene(root);
            otherDialogStage.setScene(scene);

            videoController.setNewStage(otherDialogStage);

            otherDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void setStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }
}


