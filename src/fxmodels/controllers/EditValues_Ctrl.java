package fxmodels.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EditValues_Ctrl implements Initializable {
    @FXML
    private TextField input;
    @FXML
    private Label nameEntry;
    @FXML
    private ListView<String> itemsList;
    @FXML
    private Button btnAdd,btnEdit,btnErase;
    private String fromResource;


    @Override
    public void initialize(URL location, ResourceBundle res){
        fromResource = Controllers.getEditSource();
        itemsList.getItems().addAll(Controllers.getEditValues());
        nameEntry.setText(Controllers.getNameEdit());

        itemsList.getSelectionModel().selectedItemProperty().addListener(ev -> {
            btnErase.setDisable(false);
            input.setText(itemsList.getSelectionModel().getSelectedItem());
        });
    }


    /* Buttons */
    public void editedKey(KeyEvent ev){
        if (input.getText().length() > 0){
            btnAdd.setDisable(false);
            if(itemsList.getSelectionModel().getSelectedItem() != null){ btnEdit.setDisable(false); }

        } else {
            btnAdd.setDisable(true);
            btnEdit.setDisable(true);
            btnErase.setDisable(true);
        }
    }
    public void addValue(ActionEvent ev){
        if(!itemsList.getItems().contains(input.getText())){
            itemsList.getItems().add(input.getText());
            input.setText("");
            this.editedKey(null);
        }
    }
    public void editValue(ActionEvent ev){
        if(!itemsList.getItems().contains(input.getText())){
            int index = itemsList.getSelectionModel().getSelectedIndex();
            itemsList.getItems().set(index, input.getText());
        }
    }
    public void eraseValue(ActionEvent ev){
        int index = itemsList.getSelectionModel().getSelectedIndex();
        itemsList.getItems().remove(index);
    }
    public void goBack(ActionEvent ev){
        ArrayList<String> items = new ArrayList<>();
        for (String i: itemsList.getItems()){ items.add(i); }
        
        Controllers.getOption().setArray(Controllers.getNameEdit(), items);
        Controllers.switchTo(fromResource, ev);
    }
}
