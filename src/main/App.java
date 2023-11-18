package main;
import java.io.File;

import fxmodels.controllers.Controllers;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.FileSerializer;
import util.OptionSerializer;

public class App extends Application {
    public static void main(String[] args){ launch(args); }
    
    @Override
    public void start(Stage stage){
        try {         
            FileSerializer saver = new FileSerializer(); saver.read();
            OptionSerializer option = new OptionSerializer(); option.read();
            Controllers.setSaver(saver);
            Controllers.setOption(option);

            
            Controllers.switchTo("/fxmodels/Menu.fxml", stage);
            stage.getScene().getStylesheets().add(
                getClass().getResource("/fxmodels/style.css").toExternalForm()
            );
            stage.setTitle("Detector de Metales");
            stage.getIcons().add(new Image(new File("src/data/files/icon.png").toURI().toString()));
                
        } catch(Exception e){ System.out.println("App Error:"); e.printStackTrace(); }
    }
}
