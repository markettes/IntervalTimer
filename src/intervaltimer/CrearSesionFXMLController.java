/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import accesoBD.AccesoBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import static intervaltimer.mainFXMLController.modificarpressed;
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
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;
import modelo.Gym;
import modelo.SesionTipo;

/**
 * FXML Controller class
 *
 * @author Marcos
 */
public class CrearSesionFXMLController implements Initializable {

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

    //Var's
    Grupo nuevoGrupo;
    AccesoBD database = AccesoBD.getInstance();
    Gym gimnasio = database.getGym();
    ObservableList<Grupo> gruposObs;
    ArrayList<Grupo> gruposArrayList;
    ArrayList<SesionTipo> sesionesArrayList;
    static Grupo grupoActual;
    @FXML
    private JFXTextField codSesionTextField;
    @FXML
    private JFXSlider ejercicioSlider;
    @FXML
    private JFXSlider tEjercicioSlider;
    @FXML
    private JFXSlider tCalSlider;
    @FXML
    private JFXSlider tDescCircuitoSlider;
    @FXML
    private JFXSlider tDescEjercicioSlider;
    @FXML
    private JFXSlider circuitoSlider;
    @FXML
    private JFXButton graphButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gruposArrayList = gimnasio.getGrupos();

        gruposObs = FXCollections.observableList(gruposArrayList);
        for (int i = 0; i < gruposObs.size(); i++) {
            grupoComboBox.getItems().addAll("Grupo " + gruposObs.get(i).getCodigo());
        }

        grupoComboBox.valueProperty().addListener((observable, oldVal, newVal)
                -> {
            if (newVal == null) {
                modGrupo.setDisable(true);
            } else {

                modGrupo.setDisable(false);

            }
        });
    }

    //BOTONES MENÚ
    @FXML
    private void crearGrupoAct(ActionEvent event) throws IOException {
        modificarpressed = false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void modGrupoAct(ActionEvent event) throws IOException {
        modificarpressed = true;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void crearSesionAct(ActionEvent event) {
    }

    @FXML
    private void guardarSesionAct(ActionEvent event) throws IOException {
        if (codSesionTextField.getText() == null || codSesionTextField.getText().trim().isEmpty()) {
            Alert alertCodeGroup = new Alert(Alert.AlertType.ERROR);
            alertCodeGroup.setTitle("Faltan datos");
            alertCodeGroup.setHeaderText("Por favor, introduce un código de grupo");
            alertCodeGroup.setContentText("No se puede guardar el grupo si no introduce un código de grupo numérico válido");
            alertCodeGroup.showAndWait();
        } else {
            SesionTipo nSesionTipo = new SesionTipo();
            nSesionTipo.setCodigo(codSesionTextField.getText());
            nSesionTipo.setNum_ejercicios((int)ejercicioSlider.getValue());
            nSesionTipo.setT_ejercicio((int)tEjercicioSlider.getValue());
            nSesionTipo.setT_calentamiento((int)tCalSlider.getValue());
            nSesionTipo.setD_circuito((int)tDescCircuitoSlider.getValue());
            nSesionTipo.setD_ejercicio((int)tDescEjercicioSlider.getValue());
            nSesionTipo.setNum_circuitos((int)circuitoSlider.getValue());
            
            //Introducirlo en la db
            sesionesArrayList = gimnasio.getTiposSesion();
            sesionesArrayList.add(nSesionTipo);
            gimnasio.setTiposSesion(sesionesArrayList);
            database.salvar();
            //Volvemos a la pantalla inicial
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/mainFXML.fxml"));
            anchorPane.getChildren().setAll(pane);
        }
    }

    @FXML
    private void volverSesionAct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/mainFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void graphAct(ActionEvent event) {
    }

}
