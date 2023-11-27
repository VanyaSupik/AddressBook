package com.example.demo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnExits;

    @FXML
    private Button btnNext;

    @FXML
    private TableColumn<Person, String> columnPIP;

    @FXML
    private TableView<Person> tableAddressBook;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label labelCount;

    @FXML
    private TableColumn<Person, String> columnPhone;

    private EditController editController;

    private ObservableList<Person> backupList;

    @FXML
    public void initialize() {
        columnPIP.setCellValueFactory(new PropertyValueFactory<>("pip"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateCountLabel();
            }
        });

        addressBookImpl.fillTestData();
        tableAddressBook.setItems(addressBookImpl.getPersonList());

        backupList = FXCollections.observableArrayList();
        backupList.addAll(addressBookImpl.getPersonList());
        tableAddressBook.setItems(addressBookImpl.getPersonList());


        tableAddressBook.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // обробник подій для відкриття діалогового вікна редагування
        btnEdit.setOnAction(event -> showDialogEdit(event));

        // обробник подій для кнопки додавання запису (замість тестового заповнення)
        btnAdd.setOnAction(event -> showDialogAdd(event));

        // бробник подій для кнопки видалення запису (замість тестового заповнення)
        btnDelete.setOnAction(event -> delete(event));
        btnExits.setOnAction(event -> exit(event));
        btnSearch.setOnAction(event -> actionSearch(event));
        btnNext.setOnAction(event -> next(event));
    }

    @FXML
    void actionSearch(ActionEvent event) {
        addressBookImpl.getPersonList().clear();

        for (Person person : backupList){
            if(person.getPIP().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    person.getPhone().toLowerCase().contains(txtSearch.getText().toLowerCase())) {
                addressBookImpl.getPersonList().add(person);
            }
        }
    }

    @FXML
    public void delete(ActionEvent event){
        Person selectedPerson = tableAddressBook.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження видалення");
            alert.setHeaderText("Ви впевнені, що хочете видалити цей запис?");
            alert.setContentText("Будь ласка, підтвердіть свій вибір.");

            ButtonType okButton = new ButtonType("Так", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("Ні", ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(okButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // Користувач підтвердив видалення
                addressBookImpl.delete(selectedPerson);
            }
            // Якщо користувач вибрав "Ні", нічого не робіть
        } else {
            // Якщо жоден запис не вибрано, виведіть повідомлення про помилку або сповіщення
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка видалення");
            alert.setHeaderText("Немає обраного запису");
            alert.setContentText("Будь ласка, виберіть запис для видалення.");
            alert.showAndWait();
        }
    }

    @FXML
    public void exit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Підтвердження виходу");
        alert.setHeaderText("Ви впевнені, що хочете вийти?");
        alert.setContentText("Будь ласка, підтвердіть свій вибір.");

        ButtonType okButton = new ButtonType("Так", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Ні", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(okButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            Platform.exit();
        }

    }

    public void updateCountLabel(){
        labelCount.setText("Кількість записів: " + addressBookImpl.getPersonList().size());
    }

    @FXML
    public void showDialogEdit(ActionEvent event) {
        try {
            Person selectedPerson = tableAddressBook.getSelectionModel().getSelectedItem();

            if (selectedPerson != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
                Parent root = loader.load();
                EditController editController = loader.getController();

                editController.initData(selectedPerson);

                editController.setHelloController(this);
                editController.setCollectionAddressBook(addressBookImpl);

                Stage stage = new Stage();
                stage.setTitle("Редагування/Додавання запису");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                editController.setStage(stage);
                stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showDialogAdd(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
            Parent root = loader.load();
            EditController editController = loader.getController();

            editController.setCollectionAddressBook(addressBookImpl);

            editController.setHelloController(this);

            Stage stage = new Stage();
            stage.setTitle("Редагування/Додавання запису");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            editController.setStage(stage);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("controllersButtons.fxml"));
            Parent root = loader.load();
            ButtonsController buttonsController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("ПР6");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            buttonsController.setStage(stage);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}