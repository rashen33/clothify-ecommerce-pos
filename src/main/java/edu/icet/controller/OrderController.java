package edu.icet.controller;

import edu.icet.util.CrudUtil;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OrderController implements Initializable {
    public TextField txtDate;
    public TextField txtEmpName;
    public ComboBox cmbEmpId;
    public TextField txtItemName;
    public ComboBox cmbItemCode;
    public TextField txtOrderId;
    public ComboBox cmbPayMethod;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCmbEmployeeId();
        cmbEmpId.setOnAction(actionEvent -> {
            setEmployeeName();
        });

        loadCmbItemId();
        cmbItemCode.setOnAction(actionEvent -> {
            setItemName();
        });

        loadCmbPaymentMethod();
        setDate();
//        generateId();

    }

    private void generateId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT order_id FROM orders ORDER BY id DESC LIMIT 1");

            if (resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[O]")[1]);
                num++;
                txtOrderId.setText(String.format("O%03d",num));
            }else {
                txtOrderId.setText("O001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDate() {
        Timeline date = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> txtDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        ), new KeyFrame(Duration.seconds(1)));

        date.setCycleCount(Animation.INDEFINITE);
        date.play();
    }

    private void loadCmbItemId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT item_id FROM item");
            ObservableList<String> itemId = FXCollections.observableArrayList();

            while(resultSet.next()){
                itemId.add(resultSet.getString(1));
            }
            cmbItemCode.setItems(itemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setItemName() {
        String itemId = cmbItemCode.getValue().toString();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT description FROM item where item_id = '"+itemId+"'");
            while(resultSet.next()){
                String itemName = resultSet.getString(1);
                txtItemName.setText(itemName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEmployeeName(){
        String empId = cmbEmpId.getValue().toString();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT name FROM employee where id = '"+empId+"'");
            while(resultSet.next()){
                String empName = resultSet.getString(1);
                txtEmpName.setText(empName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCmbEmployeeId(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM employee");
            ObservableList<String> employeeId = FXCollections.observableArrayList();

            while(resultSet.next()){
                employeeId.add(resultSet.getString(1));
            }
            cmbEmpId.setItems(employeeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCmbPaymentMethod(){
        ObservableList<String> obs = FXCollections.observableArrayList("Cash", "Card");
        cmbPayMethod.getItems().addAll(obs);
    }


    public void btnRemoveOrder(ActionEvent actionEvent) {
    }

    public void btnClear(ActionEvent actionEvent) {
    }

    public void btnUpdate(ActionEvent actionEvent) {
    }

    public void btnAddToCard(ActionEvent actionEvent) {
    }
}
