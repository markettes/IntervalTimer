/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;
import modelo.Sesion;

/**
 *
 * @author Marcos
 */
public class mainFXMLController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton crearGrupo;
    @FXML
    private JFXButton modGrupo;
    @FXML
    private JFXButton crearSesion;
    @FXML
    private JFXButton modSesion;
    @FXML
    private JFXComboBox<Grupo> grupoComboBox;
    @FXML
    private JFXComboBox<Sesion> sesionComboBox; //O tipo SesiónTipo
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        grupoComboBox.valueProperty().addListener((observable, oldVal, newVal) ->
        { 
            if(newVal == null){
                modGrupo.setDisable(true);
            }else{
                modGrupo.setDisable(false);
            
        } });
//        
    }    

    @FXML
    private void crearGrupoAct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void modGrupoAct(ActionEvent event) {
    }

    @FXML
    private void crearSesionAct(ActionEvent event) {
    }

    @FXML
    private void modSesionAct(ActionEvent event) {
    }
    
}
