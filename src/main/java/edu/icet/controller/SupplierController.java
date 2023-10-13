package edu.icet.controller;

import edu.icet.entity.Supplier;
import edu.icet.util.CrudUtil;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
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
    }

    public void loadCmb(){
        ObservableList<String> obs = FXCollections.observableArrayList("Mr.", "Mrs.");
        titleCmb.getItems().addAll(obs);
    }

    public void saveBtn(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(supID.getText(), supName.getText(), supContact.getText(), supCompany.getText(),titleCmb.getValue().toString());

        try {
            boolean isAdded = CrudUtil.execute("INSERT INTO supplier VALUES(?,?,?,?,?)",
                        supplier.getSupID(),
                        supplier.getSupName(),
                        supplier.getSupContactNumber(),
                        supplier.getSupCompany(),
                        supplier.getTitle());

            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Saved..!").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
