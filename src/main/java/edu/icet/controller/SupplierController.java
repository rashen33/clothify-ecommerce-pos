package edu.icet.controller;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.icet.dto.tm.SupplierTm;
import edu.icet.entity.Supplier;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    public TextField supID;
    public TextField supName;
    public TextField supContact;
    public TextField supCompany;
    public ComboBox titleCmb;
    public JFXTreeTableView tblSupplier;
    public TreeTableColumn colID;
    public TreeTableColumn colTitle;
    public TreeTableColumn colName;
    public TreeTableColumn colCompany;
    public TreeTableColumn colContactNo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new TreeItemPropertyValueFactory<>("supID"));
        colTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("supName"));
        colCompany.setCellValueFactory(new TreeItemPropertyValueFactory<>("supCompany"));
        colContactNo.setCellValueFactory(new TreeItemPropertyValueFactory<>("supContactNumber"));
        loadCmb();
        generateId();
        loadTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{
            if(null != newValue){
                setData((TreeItem<SupplierTm>) newValue);
            }
        });
    }

    public void setData(TreeItem<SupplierTm> value){
        supID.setText(value.getValue().getSupID());
        titleCmb.setValue(value.getValue().getTitle());
        supName.setText(value.getValue().getSupName());
        supCompany.setText(value.getValue().getSupCompany());
        supContact.setText(value.getValue().getSupContactNumber());
    }

    public void loadCmb(){
        ObservableList<String> obs = FXCollections.observableArrayList("Mr.", "Mrs.");
        titleCmb.getItems().addAll(obs);
    }

    public void clearFields(){
        generateId();
        supName.clear();
        supContact.clear();
        supCompany.clear();
    }

    public void saveBtn(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(supID.getText(), titleCmb.getValue().toString(), supName.getText(), supContact.getText(), supCompany.getText());

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
            boolean isUpdated = CrudUtil.execute("UPDATE supplier SET title=? , supplier_name=?, contact_number=?, company=? WHERE id=?",
                    supplier.getTitle(),
                    supplier.getSupName(),
                    supplier.getSupContactNumber(),
                    supplier.getSupCompany(),
                    supplier.getSupID()
            );

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Updated..!").show();
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

    public void deleteBtn(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(
                supID.getText(),
                titleCmb.getValue().toString(),
                supName.getText(),
                supContact.getText(),
                supCompany.getText()
        );
        try {
            boolean isDeleted = CrudUtil.execute("DELETE FROM supplier WHERE id=?",
                    supplier.getSupID());
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + supplier.getSupID() + " customer ? ", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES){
                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Supplier Deleted..!").show();
                    loadTable();
                    generateId();
                    clearFields();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Something went wrong..!").show();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void loadTable(){
        ObservableList<SupplierTm> tmList = FXCollections.observableArrayList();
        List<Supplier> list = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");

            while (resultSet.next()){
                list.add(new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                ));
            }

            for (Supplier supplier : list){
                tmList.add(new SupplierTm(
                        supplier.getSupID(),
                        supplier.getTitle(),
                        supplier.getSupName(),
                        supplier.getSupContactNumber(),
                        supplier.getSupCompany()
                ));
            }
            TreeItem<SupplierTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblSupplier.setRoot(treeItem);
            tblSupplier.setShowRoot(false);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
