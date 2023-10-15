package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.dto.tm.ItemTm;
import edu.icet.dto.tm.SupplierTm;
import edu.icet.entity.Item;
import edu.icet.entity.Supplier;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    public JFXTreeTableView itemTbl;
    public TreeTableColumn colOption;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCod.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        colD.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colSellingPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colBuyingPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("buyingPrice"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("sellingPrice"));
        colProfit.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        colProfit.setCellValueFactory(new TreeItemPropertyValueFactory<>("profit"));
        colSupId.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierId"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        generateId();
        loadCmbSupId();
        loadCmbType();
        loadCmbSize();
        loadTable();

        itemTbl.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if(null != newValue){
                setData((TreeItem<ItemTm>) newValue);
            }
        });
    }

    public void setData(TreeItem<ItemTm> value){
        txtCode.setText(value.getValue().getItemId());
        cmbSupId.setValue(value.getValue().getSupplierId());
        txtDesc.setText(value.getValue().getDesc());
        txtQty.setText(String.valueOf(value.getValue().getQty()));
        txtSellPrice.setText(String.valueOf(value.getValue().getSellingPrice()));
        txtBuyPrice.setText(String.valueOf(value.getValue().getBuyingPrice()));
        cmbSize.setValue(value.getValue().getSize());
        cmbType.setValue(value.getValue().getType());
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
                loadTable();
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

    public void loadTable(){
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        List<Item> list = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
            while (resultSet.next()){
                list.add(new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8),
                        resultSet.getString(9)

                ));
            }

            for(Item item : list){
                JFXButton btn = new JFXButton("Delete");
                btn.setBackground(Background.fill(Color.rgb(227,92,92)));
                btn.setTextFill(Color.rgb(255,255,255));
                btn.setStyle("-fx-font-weight: BOLD");

                btn.setOnAction(actionEvent -> {
                    try {
                        boolean isDeleted = CrudUtil.execute("DELETE FROM item WHERE item_id=?",
                                item.getItemId()
                        );

                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + item.getItemId() + " supplier ? ", ButtonType.YES, ButtonType.NO).showAndWait();
                        if (buttonType.get() == ButtonType.YES){
                            if (isDeleted){
                                new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted..!").show();
                                loadTable();
                                generateId();
                            }else{
                                new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                tmList.add(new ItemTm(
                    item.getItemId(),
                        item.getDesc(),
                        item.getQty(),
                        item.getBuyingPrice(),
                        item.getSellingPrice(),
                        item.getType(),
                        item.getSize(),
                        item.getProfit(),
                        item.getSupplierId(),
                        btn
                ));
            }
            RecursiveTreeItem treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            itemTbl.setRoot(treeItem);
            itemTbl.setShowRoot(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
