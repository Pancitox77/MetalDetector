package fxmodels.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Help_Ctrl implements Initializable {
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea descriptionArea;
    private String origin;

    public void initialize(URL url, ResourceBundle resources){
        origin = Controllers.getEditSource();
        titleLabel.setText(Controllers.getNameEdit());
        descriptionArea.setText(Controllers.getBodyText());
    }

    public void goBack(ActionEvent ev){ Controllers.switchTo(origin, ev); }
}
