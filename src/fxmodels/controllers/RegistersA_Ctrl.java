package fxmodels.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.RegisterObj;

public class RegistersA_Ctrl implements Initializable {
    @FXML
    private Button examineBtn;
    @FXML
    private Button eraseBtn;
    @FXML
    private TableView<RegisterObj> registersTable;
    @FXML
    private TableColumn<RegisterObj, String> userColumn;
    @FXML
    private TableColumn<RegisterObj, String> dateColumn;
    @FXML
    private TableColumn<RegisterObj, String> objColumn;
    @FXML
    private TableColumn<RegisterObj, String> materialColumn;
    @FXML
    private TableColumn<RegisterObj, String> detectorColumn;

    public void initialize(URL url, ResourceBundle resources) {
        ArrayList<RegisterObj> items = Controllers.getSaver().getRegisters();

        if (!items.isEmpty()) {
            RegisterObj[] vals = new RegisterObj[items.size()];
            for (int i = 0; i < items.size(); i++) {
                vals[i] = items.get(i);
            }
            List<RegisterObj> list = List.of(vals);

            ObservableList<RegisterObj> observableList = FXCollections.observableArrayList(list);
            registersTable.setItems(observableList);
            registersTable.getSelectionModel()
                    .selectedItemProperty().addListener(val -> {
                        examineBtn.setDisable(false);
                        eraseBtn.setDisable(false);
                    });

            // Columns factories
            userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            objColumn.setCellValueFactory(new PropertyValueFactory<>("object"));
            materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
            detectorColumn.setCellValueFactory(new PropertyValueFactory<>("detector"));
        }
    }

    public void goExamine(ActionEvent ev) {
        Controllers.setRegisterObj(registersTable.getSelectionModel().getSelectedItem());
        Controllers.switchTo("../RegistersB.fxml", ev);
    }

    public void eraseReg(ActionEvent ev) {
        Controllers.getSaver().removeRegister(registersTable.getSelectionModel().getSelectedItem());
        registersTable.getItems().remove(registersTable.getSelectionModel().getSelectedIndex());
    }

    public void goBackA(ActionEvent ev) {
        Controllers.switchTo("../Menu.fxml", ev);
    }

    public void goBackB(ActionEvent ev) {
        Controllers.switchTo("../RegistersA.fxml", ev);
    }
}
