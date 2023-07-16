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
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


public class EditCouponPageController implements Initializable {
    List<String> loadCoupons = new ArrayList<>();
    private Scene scene;
    private Stage stage;
    @FXML
    private Label editingCode;
    @FXML
    private TextField parseText;
    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;

    String coupon;
    @FXML
    public void alert(String typeOfAlert) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(typeOfAlert);
        Optional<ButtonType> result = alert.showAndWait();
    }
    @FXML
    protected void anulujButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditCoupon.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            yourToEditCoupon();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            loadCoupon();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        editingCode.setText(coupon);
    }
    public void makeCode() throws IOException {
        Kupon1 newCoupon = new Kupon1(parseText.getText(), isWin(), isUsed(), LocalDateTime.now());

        if (isCouponCorrect(newCoupon))
            switchCouponInList(newCoupon);
    }
    public boolean isWin() {
        return checkBox1.isSelected();
    }
    public boolean isUsed() {
        return checkBox2.isSelected();
    }
    private void loadCoupon() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("LotteryCopy.txt"));

        while (sc.hasNextLine()) {
            String code = sc.nextLine();
            loadCoupons.add(code);
        }
    }
    public void switchCouponInList(Kupon1 kupon1) throws IOException {
        String[] tab = coupon.split("\\) ");

        for (int i = 0; i < loadCoupons.size(); i++) {
            if (loadCoupons.get(i).equals(tab[1]))
                loadCoupons.set(i, String.valueOf(kupon1));
        }
       loadToFileCoupons();
    }
    public void loadToFileCoupons() throws IOException {
        FileWriter fileWriter = new FileWriter("LotteryCopy.txt");
        for (String k : loadCoupons) {
            fileWriter.write(k + "\n");
        }
        fileWriter.close();

        alert("Kupon został nadpisany!");
    }

    public void yourToEditCoupon() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("CouponToEdit.txt"));
        while (sc.hasNextLine())
            coupon = sc.nextLine();
    }

    private boolean isCouponCorrect(Kupon1 kupon1) {
        char[] tab = kupon1.getNumber().toCharArray();
        if (kupon1.getNumber().length() != 7) {
            alert("Długość kodu nie wynosi siedmiu znaków!");
            return false;
        }
        for (int i = 0; i < tab.length; i++) {
            if ((tab[i] < 65 && tab[i] > 122) || (tab[i] > 90 && tab[i] < 97)) {
                alert("Wprowadzono niepoprawny znak - ' " + tab[i] + " '");
                return false;
            }
        }
        return true;
    }



}