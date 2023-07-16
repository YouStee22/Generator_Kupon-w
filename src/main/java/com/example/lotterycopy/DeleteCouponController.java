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


public class DeleteCouponController implements Initializable {
    private Scene scene;
    private Stage stage;
    String currentChoose;
    List<Kupon1> loadCoupons = new ArrayList<Kupon1>();
    @FXML
    ListView<String> soutView;
    @FXML
    private Text myLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addCouponsToList();
        } catch (FileNotFoundException e) {
            System.out.println("Błąd ze znalezieniem pliku, rodzaj - " + e);
        }

        String[] tab = new String[loadCoupons.size()];

        for (int i = 0; i < tab.length; i++) {
            tab[i] = (i + 1) + ") " + loadCoupons.get(i);
        }
        soutView.getItems().addAll(tab);

        soutView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                currentChoose = soutView.getSelectionModel().getSelectedItem();
                myLabel.setText(currentChoose);
            }
        });
    }
    @FXML
    public void deleteCouponButton() throws IOException {
        List<Kupon1> newList = new ArrayList<>();

        String tx = String.valueOf(currentChoose);
        String[] tab = tx.split("\\) ");

        for (int i = 0; i < loadCoupons.size(); i++) {
            if (String.valueOf(loadCoupons.get(i)).equals(tab[1]))
                continue;
            newList.add(loadCoupons.get(i));
        }
        loadCoupons = newList;
        String[] tabs = new String[loadCoupons.size()];

        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = (i + 1) + ") " + loadCoupons.get(i);
        }

        soutView.getItems().setAll(tabs);
        saveCouponsToFile();
    }
    @FXML
    public void addCouponsToList() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("LotteryCopy.txt"));

        while (sc.hasNextLine()) {
            String tx = sc.nextLine();

            String[] tab = tx.split("\\*");
            String[] tab2 = tx.split("-");
            loadCoupons.add(new Kupon1(tab2[0], Boolean.parseBoolean(tab2[1]), Boolean.parseBoolean(tab2[2]), LocalDateTime.parse(tab[1])));
        }
    }
    @FXML
    public void alert(String typeOfAlert) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(typeOfAlert);
        Optional<ButtonType> result = alert.showAndWait();
    }
    @FXML
    protected void goBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewAdmin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveCouponsToFile() throws IOException {
        FileWriter fileWriter = new FileWriter("LotteryCopy.txt");

        for (Kupon1 k : loadCoupons) {
            fileWriter.write(k + "\n");
        }
        fileWriter.close();
    }
}