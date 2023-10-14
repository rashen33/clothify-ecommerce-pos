package edu.icet.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
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

    }

    public void printBtn(ActionEvent actionEvent) {
    }

    public void addBtn(ActionEvent actionEvent) {
    }

    public void clearBtn(ActionEvent actionEvent) {
    }

    public void saveBtn(ActionEvent actionEvent) {
    }
}
