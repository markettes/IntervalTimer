/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import accesoBD.AccesoBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;
import modelo.Gym;
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
    private JFXComboBox<String> grupoComboBox;
    @FXML
    private JFXComboBox<Sesion> sesionComboBox; //O tipo SesiónTipo
    Grupo nuevoGrupo;
    AccesoBD database = AccesoBD.getInstance();
    Gym gimnasio = database.getGym();
    ObservableList<Grupo> gruposObs;
    ArrayList<Grupo> gruposArrayList;
    
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
    private void crearGrupoAct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void modGrupoAct(ActionEvent event) {
        //TODO
    }

    @FXML
    private void crearSesionAct(ActionEvent event) {
        //TODO
    }

    @FXML
    private void modSesionAct(ActionEvent event) {
        //TODO
    }
    
}
