package com.example.lotterycopy;

import Kupon1.Kupon1;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


public class EditCouponController implements Initializable {
    private Scene scene;
    private Stage stage;
    String currentChoose;
    List<Kupon1> loadCoupons = new ArrayList<Kupon1>();
    @FXML
    ListView<String> soutView = new ListView<>();
    @FXML
    private Text myLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
             loadCoupons = addCouponsToList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        currentChoose = String.valueOf(loadCoupons.get(0));

        List<String> listToDisplay = new ArrayList<>();
        for (int i = 0; i < loadCoupons.size(); i++)
            listToDisplay.add((i + 1) + ") " + loadCoupons.get(i));


        soutView.getItems().addAll(listToDisplay);
        soutView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentChoose = soutView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentChoose);
            }
        });
    }
    public List<Kupon1> addCouponsToList() throws FileNotFoundException {
        List<Kupon1> newList = new ArrayList<Kupon1>();

        Scanner sc = new Scanner(new File("LotteryCopy.txt"));
        while (sc.hasNextLine()) {
            String tx = sc.nextLine();

            String[] tab1 = tx.split("\\*");
            String[] tab2 = tx.split("-");
            String atx = tab2[2];
            String[] tab3 = atx.split("    ");


            Kupon1 newCoupon = new Kupon1(tab2[0], Boolean.parseBoolean(tab2[1]), Boolean.parseBoolean(tab3[0]), LocalDateTime.parse(tab1[1]));
            newList.add(newCoupon);
        }
        return newList;
    }
    @FXML
    public void editCouponPage(ActionEvent event) throws IOException {
        FileWriter fileWriter = new FileWriter(new File("CouponToEdit.txt"));
        fileWriter.write(currentChoose);
        fileWriter.close();

        Parent root = FXMLLoader.load(getClass().getResource("EditCouponPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

}