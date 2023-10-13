package edu.icet.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserRegController {
    public AnchorPane userRegPane;

    public void checkAdminShowPassword(ActionEvent actionEvent) {
    }

    public void btnPasswordConfirm(ActionEvent actionEvent) {
    }

    public void btnOTPConfirm(ActionEvent actionEvent) {
    }

    public void checkUserPassword(ActionEvent actionEvent) {
    }

    public void btnBack(ActionEvent actionEvent) {
        Stage stage = (Stage) userRegPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"))));
            stage.setTitle("Dashboard");
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSave(ActionEvent actionEvent) {
    }
}
