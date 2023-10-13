package edu.icet.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    public AnchorPane rootPane;
    @FXML
    private AnchorPane dashboardPane;

    @FXML
    void employeeBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/employeeForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void itemBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/itemForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void orderBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/orderForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void orderDetailsBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/orderDetailForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void salesReportBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/salesReportForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void salesReturnBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/salesReturnForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void supplierBtn(ActionEvent event) {
        URL resource = this.getClass().getResource("/view/supplierForm.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDashBoard();
    }

    public void loadDashBoard() {
        URL resource = this.getClass().getResource("/view/dashboardRoot.fxml");
        assert resource != null;
        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
