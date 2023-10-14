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
    public ComboBox cmbSupId;
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
        generateId();
        loadCmbSupId();
        loadCmbType();
        loadCmbSize();
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



    public void loadCmbType(){
        ObservableList<String> obs = FXCollections.observableArrayList("Male", "Female", "Kids");
        cmbType.getItems().addAll(obs);
    }

    public void loadCmbSize(){
        ObservableList<String> obs = FXCollections.observableArrayList("Small", "Medium","Large");
        cmbSize.getItems().addAll(obs);
    }

    private void generateId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1");

            if (resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[S]")[1]);
                num++;
                txtCode.setText(String.format("S%03d",num));
            }else {
                txtCode.setText("S001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printBtn(ActionEvent actionEvent) {
    }

    public void addBtn(ActionEvent actionEvent) {
    }

    public void clearBtn(ActionEvent actionEvent) {
        clearFields();
    }

    public void saveBtn(ActionEvent actionEvent) {
        double profit = Double.parseDouble(txtSellPrice.getText()) - Double.parseDouble(txtBuyPrice.getText());
        Item item = new Item(
                txtCode.getText(),
                txtDesc.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtSellPrice.getText()),
                Double.parseDouble(txtBuyPrice.getText()),
                cmbType.getValue().toString(),
                cmbSize.getValue().toString(),
                profit,
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
                clearFields();
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

    public void clearFields(){
        generateId();
        txtDesc.clear();
        txtQty.clear();
        txtBuyPrice.clear();
        txtSellPrice.clear();
    }
}
