package edu.icet.controller;

import edu.icet.entity.Employee;
import edu.icet.util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    public TableColumn colId;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colNic;
    public TableColumn colDOB;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colBankAcc;
    public TableColumn colBankBranch;
    public TextField txtSearch;
    public DatePicker txtDob;
    public TextField txtContact;
    public TextField txtBankDetails;
    public TextField txtBranch;
    public TextField txtName;
    public TextField txtNic;
    public TextField txtAddress;
    public TextField txtEmployee;
    public ComboBox cmbTitle;
    public TableView tblEmployee;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateId();
        loadCmb();
        colId.setCellValueFactory(new PropertyValueFactory<>("empID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("empTitle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("empNic"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("empDob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colBankAcc.setCellValueFactory(new PropertyValueFactory<>("empBankAcNo"));
        colBankBranch.setCellValueFactory(new PropertyValueFactory<>("empBranch"));
        loadTable();

        loadTable();
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if(null != newValue){
                setTextFromTable((Employee) newValue);
            }
        });

    }

    private void setTextFromTable(Employee employee) {
        txtEmployee.setText(employee.getEmpID());
        cmbTitle.setValue(employee.getEmpTitle());
        txtName.setText(employee.getEmpName());
        txtNic.setText(employee.getEmpNic());
        txtAddress.setText(employee.getEmpAddress());
        txtDob.getEditor().setText(employee.getEmpDob());
        txtContact.setText(employee.getEmpContact());
        txtBankDetails.setText(employee.getEmpBankAcNo());
        txtBranch.setText(employee.getEmpBranch());
    }

    public void clearBtn(ActionEvent actionEvent) {
        clearFields();
    }

    public void saveBtn(ActionEvent actionEvent) {
        Employee employee = new Employee(
                txtEmployee.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtNic.getText(),
                txtAddress.getText(),
                txtDob.getValue() != null ? txtDob.getValue().toString() : null,
                txtContact.getText(),
                txtBankDetails.getText(),
                txtBranch.getText()
        );
        try {
            boolean isAdded = CrudUtil.execute("INSERT INTO employee  VALUES(?,?,?,?,?,?,?,?,?)",
                employee.getEmpID(),
                employee.getEmpTitle(),
                employee.getEmpName(),
                employee.getEmpNic(),
                employee.getEmpAddress(),
                employee.getEmpDob(),
                employee.getEmpContact(),
                employee.getEmpBankAcNo(),
                employee.getEmpBranch()
            );


            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Employee Saved..!").show();
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

    public void loadCmb(){
        ObservableList<String> obs = FXCollections.observableArrayList("Mr.", "Mrs.");
        cmbTitle.getItems().addAll(obs);
    }

    private void generateId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id FROM employee ORDER BY id DESC LIMIT 1");
            if (resultSet.next()){
                int num = Integer.parseInt(resultSet.getString(1).split("[E]")[1]);
                num++;
                txtEmployee.setText(String.format("E%03d",num));
            }else {
                txtEmployee.setText("E001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clearFields() {
        txtEmployee.clear();
        txtName.clear();
        txtNic.clear();
        txtAddress.clear();
        txtDob.setValue(null);
        txtContact.clear();
        txtBankDetails.clear();
        txtBranch.clear();
        generateId();
    }
    public void loadTable() {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee");
            while (resultSet.next()) {
                list.add(new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                ));
            }
            tblEmployee.setItems(list);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBtn(ActionEvent actionEvent) {
        Employee employee = new Employee(
                txtEmployee.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtNic.getText(),
                txtAddress.getText(),
                txtDob.getValue() != null ? txtDob.getValue().toString() : null,
                txtContact.getText(),
                txtBankDetails.getText(),
                txtBranch.getText()
        );

        try {
            boolean isDeleted = CrudUtil.execute("DELETE FROM employee WHERE id=?",
                    employee.getEmpID());
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete " + employee.getEmpID() + " employee ? ", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES){
                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Employee Deleted..!").show();
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

    public void searchBtn(ActionEvent actionEvent) {
    }

    public void updateBtn(ActionEvent actionEvent) {
        Employee employee = new Employee(
                txtEmployee.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                txtNic.getText(),
                txtAddress.getText(),
                txtDob.getValue() != null ? txtDob.getValue().toString() : null,
                txtContact.getText(),
                txtBankDetails.getText(),
                txtBranch.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE employee SET title=? , name=?, nic=?, dob=?, address=?, contact=?, bank_acc_no=?, bank_branch=? WHERE id=?",
                    employee.getEmpTitle(),
                    employee.getEmpName(),
                    employee.getEmpNic(),
                    employee.getEmpAddress(),
                    employee.getEmpDob(),
                    employee.getEmpContact(),
                    employee.getEmpBankAcNo(),
                    employee.getEmpBranch(),
                    employee.getEmpID()
            );

            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Employee Updated..!").show();
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
}
