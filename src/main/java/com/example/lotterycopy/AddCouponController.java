package com.example.lotterycopy;

import Kupon1.Kupon1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class AddCouponController {
    private Scene scene;
    private Stage stage;
    @FXML
    private TextField LabelCode;
    @FXML
    private CheckBox isWinned;
    @FXML
    private CheckBox isActive;


    @FXML
    public void createNewCouponButton() throws IOException {
        String codeCoupon = LabelCode.getText();
        boolean isWinned1 = false;
        boolean isActive1 = false;

        if (isWinned.isSelected())
            isWinned1 = true;
        if (isActive.isSelected())
            isActive1 = true;

        System.out.println(codeCoupon + " " + isWinned1 + " " + isActive1);                             //DELETE

        if (checkIfCodeIsCorrect(codeCoupon)) {
            Kupon1 newCoupon = new Kupon1(codeCoupon, isWinned1, isActive1, LocalDateTime.now());

            List<Kupon1> newList = new ArrayList<Kupon1>();
            Scanner sc = new Scanner(new File("LotteryCopy.txt"));

            String outString = "";
            while (sc.hasNextLine()) {
                String tx = sc.nextLine();

                String[] tab = tx.split("\\*");
                String[] tab2 = tx.split("-");

                newList.add(new Kupon1(tab2[0], Boolean.parseBoolean(tab2[1]), Boolean.parseBoolean(tab2[2]), LocalDateTime.parse(tab[1])));
                outString += tx + "\n";
            }
            newList.add(newCoupon);
            outString += newCoupon + "\n";

            for (Kupon1 k : newList)
                System.out.println(k);

            FileWriter fileWriter = new FileWriter("LotteryCopy.txt");
            fileWriter.write(outString);
            fileWriter.close();
        }
    }



    public boolean checkIfCodeIsCorrect(String codeCoupon) throws FileNotFoundException {
        if (codeCoupon.length() != 7) {
            alert("Długość kodu kuponu jest niepoprawna!");
            LabelCode.setText("Wprowadź ponownie dane");
            return false;
        }
        Scanner sc = new Scanner(new File("LotteryCopy.txt"));
        while (sc.hasNextLine()) {
            String tx = sc.nextLine();

            String[] tab = tx.split("-");
            if (tab[0].equals(LabelCode.getText())) {
                alert("Coupon with same code already exists!");
                LabelCode.setText("Wprowadź ponownie dane");
                return false;
            }
        }
        char[] tab = codeCoupon.toCharArray();

        for (int i = 0; i < tab.length; i++) {
            if ((tab[i] < 65 && tab[i] > 122) || (tab[i] > 90 && tab[i] < 97)) {
                alert("Wprowadzono niepoprawny znak - ' " + tab[i] + " '");
                return false;
            }
        }
        return true;
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
}
