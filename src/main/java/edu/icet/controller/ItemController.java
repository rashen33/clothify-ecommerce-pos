package edu.icet.controller;

import com.jfoenix.controls.JFXTreeTableView;
import edu.icet.entity.Item;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.ResourceBundle;

public class ItemController implements Initializable {
    public TextField txtCode;
    public TextField txtDesc;
    public TextField txtQty;
    public TextField txtAddQty;
    public TextField txtBuyPrice;
    public TextField txtSellPrice;
    public ComboBox cmbType;
    public ComboBox cmbSize;
    public TextField txtProfit;
    public ComboBox cmbSupId;
    public ComboBox cmbSupName;
    public TreeTableColumn colCod;
    public TreeTableColumn colD;
    public TreeTableColumn colQty;
    public TreeTableColumn colSellingPrice;
    public TreeTableColumn colBuyingPrice;
    public TreeTableColumn colSize;
    public TreeTableColumn colType;
    public TreeTableColumn colProfit;
    public TreeTableColumn colSupId;
    public TreeTableColumn colOp;
    public JFXTreeTableView itemTbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCmbSupId();
        loadCmbSupName();
        loadCmbType();
        loadCmbSize();
    }

    public void printBtn(ActionEvent actionEvent) {
    }

    public void addBtn(ActionEvent actionEvent) {
    }

    public void clearBtn(ActionEvent actionEvent) {
    }

    public void saveBtn(ActionEvent actionEvent) {
        Item item = new Item(
                txtCode.getText(),
                txtDesc.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtSellPrice.getText()),
                Double.parseDouble(txtBuyPrice.getText()),
                cmbType.getValue().toString(),
                cmbSize.getValue().toString(),
                Double.parseDouble(txtProfit.getText().toString()),
                cmbSupId.getValue().toString());

        try {
            boolean isAdded = CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?,?,?,?,?,?)",
                    item.getItemId(),
                    item.getDesc(),
                    item.getQty(),
                    item.getSellingPrice(),
                    item.getBuyingPrice(),
                    item.getType(),
                    item.getSize(),
                    item.getProfit(),
                    item.getSupplierId());
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved..!").show();
//                clearFields();
//                loadTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCmbSupId(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM supplier");
            ObservableList<String> supplierId = FXCollections.observableArrayList();

            while(resultSet.next()){
                supplierId.add(resultSet.getString(1));
            }

            cmbSupId.setItems(supplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCmbSupName(){
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT supplier_name FROM supplier");
            ObservableList<String> supplierName = FXCollections.observableArrayList();

            while(resultSet.next()){
                supplierName.add(resultSet.getString(1));
            }

            cmbSupName.setItems(supplierName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCmbType(){
        ObservableList<String> obs = FXCollections.observableArrayList("Male", "Female", "Kids");
        cmbType.getItems().addAll(obs);
    }

    public void loadCmbSize(){
        ObservableList<String> obs = FXCollections.observableArrayList("Small", "Medium","Large");
        cmbSize.getItems().addAll(obs);
    }
}
