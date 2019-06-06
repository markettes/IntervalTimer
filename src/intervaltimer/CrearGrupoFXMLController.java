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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;
import modelo.Gym;
import modelo.Sesion;
import modelo.SesionTipo;


/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class CrearGrupoFXMLController implements Initializable {

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
    private JFXComboBox<Sesion> sesionComboBox;
    @FXML
    private JFXTextField codgrupoTextField;
    @FXML
    private JFXTextArea descTextArea;
    @FXML
    private JFXComboBox<String> sesionTipoComboBox;
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
    private void guardarGrupoAct(ActionEvent event) throws IOException {
        if(codgrupoTextField.getText() == null || codgrupoTextField.getText().trim().isEmpty()){
            Alert alertCodeGroup = new Alert(Alert.AlertType.ERROR);
            alertCodeGroup.setTitle("Faltan datos");
            alertCodeGroup.setHeaderText("Por favor, introduce un código de grupo");
            alertCodeGroup.setContentText("No se puede guardar el grupo si no introduce un código de grupo numérico válido");
            alertCodeGroup.showAndWait();
        }else if(descTextArea.getText() == null || descTextArea.getText().trim().isEmpty()){
            Alert alertDescGroup = new Alert(Alert.AlertType.INFORMATION);
            alertDescGroup.setTitle("Faltan datos");
            alertDescGroup.setHeaderText("Por favor, introduce una descripción");
            alertDescGroup.setContentText("No se puede guardar el grupo si no introduce una descripción");
            alertDescGroup.showAndWait();
        }else{
            nuevoGrupo = new Grupo();
            nuevoGrupo.setCodigo(codgrupoTextField.getText());
            nuevoGrupo.setDescripcion(descTextArea.getText());
            
            gruposArrayList.add(nuevoGrupo);
            
            gimnasio.setGrupos(database.getGym().getGrupos());
            database.salvar();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/mainFXML.fxml"));
            anchorPane.getChildren().setAll(pane);
        }
        
        
    }

    @FXML
    private void volverGrupoAct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/mainFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
    
}
