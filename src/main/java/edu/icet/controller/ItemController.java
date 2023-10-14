package edu.icet.controller;

import com.jfoenix.controls.JFXTreeTableView;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    }

    public void printBtn(ActionEvent actionEvent) {
    }

    public void addBtn(ActionEvent actionEvent) {
    }

    public void clearBtn(ActionEvent actionEvent) {
    }

    public void saveBtn(ActionEvent actionEvent) {
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
}
