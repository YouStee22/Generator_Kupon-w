package com.example.lotterycopy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class SortCouponsController implements Initializable {
    Scene scene;
    Stage stage;
    @FXML
    private TextField textFieldFrom1;
    @FXML
    private TextField textFieldFrom2;
    @FXML
    private TextField textFieldTo1;
    @FXML
    private TextField textFieldTo2;
    @FXML
    private ListView<String> displayView;
    List<String> couponList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadCouponsToList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void searchStageOne() {


        if (checkIfItsFiiled()  &&  checkFormat()) {
            LocalTime from1 = LocalTime.parse(textFieldFrom1.getText());
            LocalDate from2 = LocalDate.parse(textFieldFrom2.getText());

            LocalDateTime from = LocalDateTime.of(from2, from1);

            LocalTime to1 = LocalTime.parse(textFieldTo1.getText());
            LocalDate to2 = LocalDate.parse(textFieldTo2.getText());

            LocalDateTime to = LocalDateTime.of(to2, to1);


            searchFromCoupons(from, to);
        }
    }
    public void searchFromCoupons(LocalDateTime from, LocalDateTime to) {

        List<String> couponsWithSortedDate = new ArrayList<>();
        for (int i = 0; i < couponList.size(); i++) {
            String[] tab = couponList.get(i).split("\\*");
            LocalDateTime l1 = LocalDateTime.parse(tab[1]);


            if (l1.isAfter(from)  &&  l1.isBefore(to))
                couponsWithSortedDate.add(couponList.get(i));

            displayView.getItems().setAll(couponsWithSortedDate);
        }
    }
    public void loadCouponsToList() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("LotteryCopy.txt"));

        while (sc.hasNextLine())
            couponList.add(sc.nextLine());

        displayView.getItems().addAll(couponList);
    }
    @FXML
    protected void goBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewAdmin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void alert(String typeOfAlert) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(typeOfAlert);
        Optional<ButtonType> result = alert.showAndWait();
    }
    public boolean checkIfItsFiiled() {
        if (textFieldFrom1.getText().isBlank()  ||  textFieldFrom2.getText().isBlank()
                ||  textFieldTo1.getText().isBlank()  ||  textFieldTo2.getText().isBlank()) {
            alert("Pola nie zostały wypełnione");
            return false;
        }
        return true;
//
//
//        if (textFieldFrom1.getText().isBlank()  &&  !textFieldTo1.getText().isBlank()  ||
//        !textFieldFrom1.getText().isBlank()  &&  textFieldTo1.getText().isBlank()) {
//            alert("Musisz uzupełnic minimum dwa pola tej same daty");
//            return false;
//        }
//
//        if (textFieldFrom2.getText().isBlank()  &&  !textFieldTo2.getText().isBlank()  ||
//                !textFieldFrom2.getText().isBlank()  &&  textFieldTo2.getText().isBlank()) {
//            alert("Musisz uzupełnic minimum dwa pola tej same daty");
//            return false;
//        }
    }
    public boolean checkFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm:ss");
        try {
            formatter1.parse(textFieldFrom1.getText());
            formatter.parse(textFieldFrom2.getText());

            formatter1.parse(textFieldTo1.getText());
            formatter.parse(textFieldTo2.getText());
            return true;
        } catch (Exception e) {
            alert("Wprowadzono błędny format daty!");
        }
        return false;
    }
}

