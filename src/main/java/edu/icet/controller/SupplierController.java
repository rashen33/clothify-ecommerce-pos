package edu.icet.controller;

import edu.icet.entity.Supplier;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    public TextField supID;
    public TextField supName;
    public TextField supContact;
    public TextField supCompany;
    public ComboBox titleCmb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCmb();
        generateId();
    }

    public void loadCmb(){
        ObservableList<String> obs = FXCollections.observableArrayList("Mr.", "Mrs.");
        titleCmb.getItems().addAll(obs);
    }

    public void clearFields(){
        supID.clear();
        generateId();
        supName.clear();
        supContact.clear();
        supCompany.clear();
    }

    public void saveBtn(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(supID.getText(), supName.getText(), supContact.getText(), supCompany.getText(),titleCmb.getValue().toString());

        try {
            boolean isAdded = CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?)",
                        supplier.getSupID(),
                        supplier.getTitle(),
                        supplier.getSupName(),
                        supplier.getSupContactNumber(),
                        supplier.getSupCompany());

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Saved..!").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");

            if (resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[S]")[1]);
                num++;
                supID.setText(String.format("S%03d",num));
            }else {
                supID.setText("S001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearBtn(ActionEvent actionEvent) {
        supID.clear();
        supName.clear();
        supContact.clear();
        supCompany.clear();
    }

    public void updateBtn(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                supID.getText(),
                titleCmb.getValue().toString(),
                supName.getText(),
                supContact.getText(),
                supCompany.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE customer SET name=? , address=?, salary=? WHERE id=?",
                    supplier.getSupID(),
                    supplier.getTitle(),
                    supplier.getSupContactNumber(),
                    supplier.getSupCompany()
            );

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated..!").show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBtn(ActionEvent actionEvent) {
    }
}
