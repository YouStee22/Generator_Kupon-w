package com.example.lotterycopy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class HelloConAdmin {
    private Scene scene;
    private Stage stage;

    @FXML
    public void addCouponButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddCoupon.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void deleteCoupon(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DeleteCoupon.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void editCoupon(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditCoupon.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sortCoupon(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SortCoupon.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void goBackButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View1.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
