package fxmodels.controllers;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.FileSerializer;
import util.OptionSerializer;
import util.RegisterObj;

public class Controllers {
    private static Stage stage;
    private static RegisterObj tempObj;
    private static RegisterObj registerObj;
    private static FileSerializer saver;
    private static OptionSerializer option;

    private static ArrayList<String> editValues;
    private static String editSource;
    private static String nameEdit;
    private static String bodyText;

    /* Switch funcs */
    public static void switchTo(String resource, Event event) {
        try {
            Parent root = FXMLLoader.load(Controllers.class.getResource(resource));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    Controllers.class.getResource("../style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Er Contr");
            ex.printStackTrace();
        }
    }

    public static void switchTo(String resource, Stage origin) {
        try {
            Parent root = FXMLLoader.load(Controllers.class.getResource(resource));
            stage = origin;
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception ex) {
            System.out.println("Err3 contr" + ex.getMessage());
        }
    }

    /* Utility funcs */
    public static void setSaver(FileSerializer serial) {
        saver = serial;
        tempObj = new RegisterObj();
    }

    public static RegisterObj getTempObj() {
        return tempObj;
    }

    public static void setTempObj(RegisterObj obj) {
        tempObj = obj;
    }

    public static FileSerializer getSaver() {
        return saver;
    }

    public static void setStage(Stage stg) {
        stage = stg;
    }

    public static Stage getStage() {
        return stage;
    }

    public static OptionSerializer getOption() {
        return option;
    }

    public static void setOption(OptionSerializer option) {
        Controllers.option = option;
    }

    /* EditValues funcs */
    public static String getNameEdit() {
        return nameEdit;
    }

    public static void setNameEdit(String n) {
        nameEdit = n;
    }

    public static ArrayList<String> getEditValues() {
        return editValues;
    }

    public static void setEditValues(ArrayList<String> values) {
        editValues = values;
    }

    public static String getEditSource() {
        return editSource;
    }

    public static void setEditSource(String source) {
        editSource = source;
    }

    /* Other data funcs */
    public static RegisterObj getRegisterObj() {
        return registerObj;
    }

    public static void setRegisterObj(RegisterObj registerObj) {
        Controllers.registerObj = registerObj;
    }

    public static String getBodyText() {
        return bodyText;
    }

    public static void setBodyText(String body) {
        bodyText = body;
    }
}
