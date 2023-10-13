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
            stage.setTitle("Dashboard");
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void linkRegister(ActionEvent actionEvent) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/userRegForm.fxml"))));
            stage.setTitle("User Registration");
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void linkForgotPw(ActionEvent actionEvent) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/forgetPWForm.fxml"))));
            stage.setTitle("Forget Password");
            stage.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
