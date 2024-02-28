package fxmodels.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import util.OptionSerializer;

public class NewB_Ctrl implements Initializable {
    @FXML
    private ListView<String> placesList;
    @FXML
    private ListView<String> detectorsList;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Spinner<Integer> idNum;
    private OptionSerializer option;

    private static final String PLACES_KEY = "Lugares";
    private static final String DETECTORS_KEY = "Detectores";

    public void initialize(URL location, ResourceBundle resources) {
        option = Controllers.getOption();

        // Clear items (to avoid duplicates)
        placesList.getItems().clear();
        detectorsList.getItems().clear();

        // Default values
        placesList.getItems().addAll("Parque", "Playa");
        detectorsList.getItems().add("A simple vista");
        idNum.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0, 300, Controllers.getTempObj().getId()));

        // Selec default values
        placesList.getSelectionModel().select(0);
        detectorsList.getSelectionModel().select(0);

        // Listeners
        placesList.getSelectionModel().selectedItemProperty().addListener(new PlacesListener());
        detectorsList.getSelectionModel().selectedItemProperty().addListener(new DetectorsListener());
        idNum.valueProperty().addListener(new IdListener());

        // Add items from file
        placesList.getItems().addAll(option.getArray(PLACES_KEY));
        detectorsList.getItems().addAll(option.getArray(DETECTORS_KEY));
        descriptionArea.setText(Controllers.getTempObj().getDescription());
    }

    /* Buttons funcs */
    public void editPlaces(MouseEvent ev) {
        if (ev.getButton() == MouseButton.SECONDARY) {
            switchEdit(option.getArray(PLACES_KEY), PLACES_KEY, ev);
        }
    }

    public void editDetectors(MouseEvent ev) {
        if (ev.getButton() == MouseButton.SECONDARY) {
            switchEdit(option.getArray(DETECTORS_KEY), DETECTORS_KEY, ev);
        }
    }

    public void goBack(ActionEvent ev) {
        Controllers.switchTo("../NewA.fxml", ev);
    }

    public void goFinish(ActionEvent ev) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registro guardado");
        alert.setContentText("Registro guardado. Accede a él desde 'Registros'");
        alert.showAndWait();

        Controllers.getTempObj().setDescription(descriptionArea.getText());
        Controllers.getSaver().addRegister(Controllers.getTempObj());
        Controllers.switchTo("../Menu.fxml", ev);
    }

    /* Utility funcs */
    private void switchEdit(ArrayList<String> items, String name, Event ev) {
        Controllers.setEditSource("../NewB.fxml");
        Controllers.setEditValues(items);
        Controllers.setNameEdit(name);
        Controllers.switchTo("../EditValues.fxml", ev);
    }

    /* Listeners */
    private class PlacesListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            Controllers.getTempObj().setPlace(placesList.getSelectionModel().getSelectedItem());
        }
    }

    private class DetectorsListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            Controllers.getTempObj().setDetector(detectorsList.getSelectionModel().getSelectedItem());
        }
    }

    private class IdListener implements ChangeListener<Integer> {
        @Override
        public void changed(ObservableValue<? extends Integer> arg0, Integer arg1, Integer arg2) {
            Controllers.getTempObj().setId(idNum.getValue());
        }
    }
}
