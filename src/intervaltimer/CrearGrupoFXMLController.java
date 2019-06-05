/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;

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
    private JFXComboBox<?> grupoComboBox;
    @FXML
    private JFXComboBox<?> sesionComboBox;
    @FXML
    private JFXTextField codgrupoTextField;
    @FXML
    private JFXTextArea descTextArea;
    Grupo nuevoGrupo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        if(codgrupoTextField.getText() == null || codgrupoTextField.getText().trim().isEmpty()){
            Alert alertCodeGroup = new Alert(Alert.AlertType.INFORMATION);
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
            nuevoGrupo.setCodigo(codgrupoTextField.toString());
            nuevoGrupo.setDescripcion(descTextArea.toString());
            //nuevoGrupo.setDefaultTipoSesion();
        }
        
        
    }

    @FXML
    private void volverGrupoAct(ActionEvent event) {
    }
    
}
