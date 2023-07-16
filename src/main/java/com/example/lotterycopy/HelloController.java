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
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class HelloController implements Initializable{
    static int HOW_MANY_CUPONS;
    static List<String> coupons = new ArrayList<>();
    private Scene scene;
    private Stage stage;

    @FXML
    private TextField QuantityOfCoupons;
    @FXML
    ListView<String> DisplayView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            displayCoupons();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer setQuantityOfCoupons() {
        int out = 0;
        if (!QuantityOfCoupons.getText().isBlank()) {
            System.out.println(QuantityOfCoupons.getText());
            out = Integer.parseInt(QuantityOfCoupons.getText());
        }
        else
            alert("Field with quanitity of coupons must be filled!");
        return out;
    }
    public void file() throws IOException {
        FileWriter fileWriter = new FileWriter("LotteryCopy.txt");
        Random random = new Random();

        while (HOW_MANY_CUPONS > 0) {

            fileWriter.write((new Kupon1(generateNumberOfCoupons(), random.nextBoolean(), random.nextBoolean(), LocalDateTime.now())) + "\n");
            HOW_MANY_CUPONS--;
        }
        fileWriter.close();
    }
    public String generateNumberOfCoupons() {
        Random random = new Random();
        boolean choose;

        String kupon = "";

        for (int i = 0; i <= 6; i++) {
            choose = random.nextBoolean();

            if (choose)
                kupon += random.nextInt(0, 10);
            if (!choose) {
                int atx = random.nextInt(65, 123);
                while (atx > 90 && atx < 97)
                    atx = random.nextInt(65, 123);

                kupon += (char) atx;
            }
        }
        return kupon;
    }
    @FXML
    protected void onAdminButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewAdmin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onButtonClick() throws IOException {
        HOW_MANY_CUPONS = setQuantityOfCoupons();
        file();

        displayCoupons();

    }
    @FXML
    public void displayCoupons() throws FileNotFoundException {
        coupons.clear();
        DisplayView.getItems().clear();
        Scanner sc = new Scanner(new File("LotteryCopy.txt"));
        while (sc.hasNextLine()) {
            coupons.add(sc.nextLine());
        }
        List<String> outList = new ArrayList<>();
        outList.addAll(coupons);

        DisplayView.getItems().addAll(outList);
    }
    @FXML
    public void alert(String typeOfAlert) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(typeOfAlert);
        Optional<ButtonType> result = alert.showAndWait();
    }
}