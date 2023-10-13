package edu.icet.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public AnchorPane loginPane;

    public void btnLogin(ActionEvent actionEvent) {
        //add the login logic later
        Stage stage = (Stage) loginPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void linkRegister(ActionEvent actionEvent) {
    }

    public void linkForgotPw(ActionEvent actionEvent) {
    }
}
