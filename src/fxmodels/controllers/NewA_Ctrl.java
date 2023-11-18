package fxmodels.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.OptionSerializer;
import util.RegisterObj;

public class NewA_Ctrl implements Initializable {
    @FXML
    private ListView<String> objectsList,materialsList;
    @FXML
    private ComboBox<String> userChoice;
    @FXML
    private DatePicker dateShow;
    @FXML
    private TextField hourEntry;
    @FXML
    private ImageView photo1,photo2,photo3;
    private OptionSerializer option;
    
    public void initialize(URL location, ResourceBundle resources){
        option = Controllers.getOption();

        //Clear items (to avoid duplicates)
        objectsList.getItems().clear();
        materialsList.getItems().clear();
        userChoice.getItems().clear();

        //Default values
        objectsList.getItems().add("Desconocido");
        materialsList.getItems().add("Desconocido");
        userChoice.getItems().add("Invitado");

        //Selec default values
        objectsList.getSelectionModel().select(0);
        materialsList.getSelectionModel().select(0);
        userChoice.getSelectionModel().select(0);
        dateShow.setValue(LocalDate.now());

        //Set default images
        Image defaultImg = new Image(getClass().getResourceAsStream("../../data/files/default-img.png"));
        String[] images = Controllers.getTempObj().getImagesPath();
        
        //Verify if images  exists and are not null
        File i1 = new File((images[0] != null ? images[0] : "src/data/files/default-img.png"));
        File i2 = new File((images[1] != null ? images[1] : "src/data/files/default-img.png"));
        File i3 = new File((images[2] != null ? images[2] : "src/data/files/default-img.png"));
        
        Image res1 = (i1.exists() ? new Image(i1.toURI().toString()) : defaultImg);
        Image res2 = (i2.exists() ? new Image(i2.toURI().toString()) : defaultImg);
        Image res3 = (i3.exists() ? new Image(i3.toURI().toString()) : defaultImg);
        
        photo1.setImage(res1);
        photo2.setImage(res2);
        photo3.setImage(res3);

        //Listeners
        objectsList.getSelectionModel().selectedItemProperty().addListener(new ObjectListener());
        materialsList.getSelectionModel().selectedItemProperty().addListener(new MaterialListener());
        userChoice.getSelectionModel().selectedItemProperty().addListener(new UserListener());

        //Add items from file
        objectsList.getItems().addAll(option.getArray("Objetos"));
        materialsList.getItems().addAll(option.getArray("Materiales"));
        userChoice.getItems().addAll(option.getArray("Usuarios"));
    }

    
    /* Buttons funcs */
    public File askPhotoFile(){
        //Init file chooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccione una imágen");
        chooser.getExtensionFilters().addAll(
            new ExtensionFilter("Imágenes", new String[]{"*.jpeg","*.png","*.jpg"}),
            new ExtensionFilter("Todos los archivos", "*.*")
        );
        File selected = chooser.showOpenDialog(Controllers.getStage());

        //If image is not null...
        if(selected != null){
            //Get extensión of the image
            int indexExtension = selected.getName().lastIndexOf(".");
            String extension = selected.getName().substring(indexExtension+1);
            String[] availableExtensions = new String[]{"jpg","png","jpeg"};

            //See if extension of file is an image
            for (int i=0; i<availableExtensions.length; i++){
                if (extension.equals(availableExtensions[i])){
                    //Extension of a image. Correct
                    return selected;
                }
            }

            //Extension not in available extension list
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Formato de archivo no permitido");
            alert.setContentText(
                "El formato del archivo {"+extension+"} no está permitido. "+
                "Los formatos permitidos son jpg, png y jpeg."
            );
            alert.showAndWait();
        } return null;
    }

    public void setPhoto1(MouseEvent ev){
        File selectedPhoto = this.askPhotoFile();
        if(selectedPhoto != null){
            String fileUrl = selectedPhoto.toURI().toString();
            fileUrl = copyPhoto(selectedPhoto);

            // photo is valid
            Image img = new Image(fileUrl);
            photo1.setImage(img);

            String[] paths = Controllers.getTempObj().getImagesPath();
            paths[0] = fileUrl;
            Controllers.getTempObj().setImagesPath(paths);
        }
    }

    public void setPhoto2(MouseEvent ev){
        File selectedPhoto = this.askPhotoFile();
        if(selectedPhoto != null){
            String fileUrl = selectedPhoto.toURI().toString();
            fileUrl = copyPhoto(selectedPhoto);

            // photo is valid
            Image img = new Image(fileUrl);
            photo2.setImage(img);

            // save url
            String[] paths = Controllers.getTempObj().getImagesPath();
            paths[1] = fileUrl;
            Controllers.getTempObj().setImagesPath(paths);
        }
    }

    public void setPhoto3(MouseEvent ev){
        File selectedPhoto = this.askPhotoFile();
        if(selectedPhoto != null){
            String fileUrl = selectedPhoto.toURI().toString();
            fileUrl = copyPhoto(selectedPhoto);

            // photo is valid
            Image img = new Image(fileUrl);
            photo3.setImage(img);

            String[] paths = Controllers.getTempObj().getImagesPath();
            paths[2] = fileUrl;
            Controllers.getTempObj().setImagesPath(paths);
        }
    }

    public void editObjects(MouseEvent ev){
        if (ev.getButton() == MouseButton.SECONDARY){
            switchEdit(option.getArray("Objetos"),"Objetos", ev);
        }
    }

    public void editMaterials(MouseEvent ev){
        if (ev.getButton() == MouseButton.SECONDARY){
            switchEdit(option.getArray("Materiales"),"Materiales",ev);
        }
    }

    public void goBack(ActionEvent ev){
        Controllers.setTempObj(new RegisterObj());
        Controllers.switchTo("../Menu.fxml", ev);
    }

    public void goNext(ActionEvent ev){
        String[] images = Controllers.getTempObj().getImagesPath();
        for(int i=0; i<images.length; i++){
            if (images[i] == null){
                images[i] = getClass().getResource("../../data/files/default-img.png").toExternalForm();
            }
        }
        Controllers.getTempObj().setDate(dateShow.getValue());
        Controllers.switchTo("../NewB.fxml", ev); }


    /* Utility funcs */
    private void switchEdit(ArrayList<String> items, String name, Event ev){
        Controllers.setEditSource("../NewA.fxml");
        Controllers.setEditValues(items);
        Controllers.setNameEdit(name);
        Controllers.switchTo("../EditValues.fxml", ev);
    }

    private String copyPhoto(File selectedPhoto){
        File destiny = selectedPhoto;
        try {
            //if no error, copy file in 'src/data/files' and save the url-path
            destiny = new File("src/data/user/"+destiny.getName());
            destiny.createNewFile();

            FileOutputStream output = new FileOutputStream(destiny);
            Files.copy(Path.of(selectedPhoto.toURI()), output);
            
            output.close();
            return destiny.toURI().toString();
        }
        catch (FileNotFoundException notFoundEx){ System.out.println("Not found - NewA"); }
        catch (IOException ex){ ex.printStackTrace(); }
        return selectedPhoto.toURI().toString();
    }


    /* Listeners */
    private class UserListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            Controllers.getTempObj().setUser(userChoice.getSelectionModel().getSelectedItem());
        }
    }
    private class ObjectListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            Controllers.getTempObj().setObject(objectsList.getSelectionModel().getSelectedItem());
        }
    }
    private class MaterialListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
            Controllers.getTempObj().setMaterial(materialsList.getSelectionModel().getSelectedItem());
        }
    }
}
