package fxmodels.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import util.RegisterObj;

public class Menu_Ctrl {
    @FXML
    private Button btnNew;
    @FXML
    private Button btnRegisters;
    
    public void focusBtn(MouseEvent ev){ ((Button) ev.getSource()).requestFocus(); }
    public void switchHelp(String name,String content){
        Controllers.setEditSource("../Menu.fxml");
        Controllers.setNameEdit(name);
        Controllers.setBodyText(content);
        Controllers.switchTo("../Help.fxml", Controllers.getStage());
    }


    /* Menu: Registers */
    public void showUsers(ActionEvent e){
        Controllers.setEditSource("../Menu.fxml");
        Controllers.setNameEdit("Usuarios");
        Controllers.setEditValues(Controllers.getOption().getArray("Usuarios"));
        Controllers.switchTo("../EditValues.fxml", Controllers.getStage());
    }
    public void newMenu(ActionEvent e){ btnNew.fire(); }
    public void registersMenu(ActionEvent e){ btnRegisters.fire(); }
    public void exitMenu(ActionEvent e){ Controllers.getStage().close(); }


    /* Menu: Data */
    public void clearRegisters(ActionEvent ev){
        Controllers.getSaver().clearRegister();
        Controllers.setTempObj(new RegisterObj());        
        new Alert(AlertType.INFORMATION, "Registros eliminados").showAndWait();
    }
    public void clearOptions(ActionEvent ev){
        Controllers.getOption().clearOptions();
        new Alert(AlertType.INFORMATION, "Valores por defecto eliminados").showAndWait();
    }


    /* Menu: Help */
    public void showAbout(ActionEvent ev){
        String content = "Guarda cualquier objeto que encuentres con tu detector "+
            "de metales en un archivo. Accede y modifica los registros, o agrega "+
            "nuevos.\n\nTodos los parámetros son opcionales, algunas ya vienen "+
            "por defecto y otros no son necesarios (como la descripción o las "+
            "imágenes.\n\n"+
            "Para crear y guardar un nuevo registro, accede a 'Nuevo'.\n\t"+
            " En la primera pantalla, selecciona el objeto hallado, su material, "+
            "sube imágenes, elige la fecha, la hora y finalmente el usuario.\n\t"+
            " En la segunda pantalla, selecciona el lugar (de referencia),el detector "+
            "usado,escribe una descripción y coloca el ID dado por el detector (no todos "+
            "los detectores incluyen esta función).\n\n"+
            "Para ver una tabla de todos los registros, selecciona el botón 'Registros'. "+
            "Este mostrará algunos de los detalles de cada registro encontrado. Si "+
            "seleccionas uno de los registros, podrás ver toda su información,además de "+
            "poder editarla.";
        this.switchHelp("Acerca De", content);
    }
    public void showVersion(ActionEvent ev){
        String content = "Programa en fase Beta v0.1";
        this.switchHelp("Versión", content);
    }
    public void showLicense(ActionEvent ev){
        String content = "Este programa usa una licencia BSD";
        this.switchHelp("Licencia", content);
    }

    
    /* Buttons */
    public void newRegister(ActionEvent ev){ Controllers.switchTo("../NewA.fxml", ev); }
    public void seeRegisters(ActionEvent ev){ Controllers.switchTo("../RegistersA.fxml", ev); }
}
