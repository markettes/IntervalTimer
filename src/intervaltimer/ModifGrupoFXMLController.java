/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import accesoBD.AccesoBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;
import modelo.Gym;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class ModifGrupoFXMLController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXComboBox<String> grupoComboBox;
    @FXML
    private JFXComboBox<String> sesionComboBox;
    @FXML
    private JFXButton crearGrupo;
    @FXML
    private JFXButton modGrupo;
    @FXML
    private JFXButton crearSesion;
    @FXML
    private JFXButton modSesion;
    @FXML
    private JFXTextField codgrupoTextField;
    @FXML
    private JFXTextArea descTextArea;
     Grupo nuevoGrupo;
    AccesoBD database = AccesoBD.getInstance();
    Gym gimnasio = database.getGym();
    ObservableList<Grupo> gruposObs;
    ArrayList<Grupo> gruposArrayList;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        gruposArrayList = database.getGym().getGrupos();
        
        gruposObs = FXCollections.observableList(gruposArrayList);
         for(int i = 0; i < gruposObs.size(); i++){
            grupoComboBox.getItems().addAll("Grupo " + gruposObs.get(i).getCodigo());
        }
        
        
        grupoComboBox.valueProperty().addListener((observable, oldVal, newVal) ->
        { 
            if(newVal == null){
                modGrupo.setDisable(true);
            }else{
                
                modGrupo.setDisable(false);
                
               
            
        } });
    }    

    @FXML
    private void crearGrupoAct(ActionEvent event) {
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

    @FXML
    private void guardarGrupoAct(ActionEvent event) {
    }

    @FXML
    private void volverGrupoAct(ActionEvent event) {
    }
    
}
