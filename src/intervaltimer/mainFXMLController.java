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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Grupo;
import modelo.Gym;
import modelo.SesionTipo;

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
    private JFXComboBox<String> grupoComboBox;
    @FXML
    private JFXComboBox<String> sesionComboBox; //O tipo SesiónTipo
    Grupo nuevoGrupo;
    AccesoBD database = AccesoBD.getInstance();
    Gym gimnasio = database.getGym();
    ObservableList<Grupo> gruposObs;
    ArrayList<Grupo> gruposArrayList;
    static boolean modificarpressed;
    static Grupo grupoActual;
    @FXML
    private JFXButton graphButton;
    ArrayList<SesionTipo> sesionesArrayList;
    ObservableList<SesionTipo> sesionesObs;
    @FXML
    private Label timelabel;
    @FXML
    private JFXButton startButton;
    @FXML
    private JFXButton pauseButton;
    @FXML
    private JFXButton nextButton;
    @FXML
    private JFXButton resetButton;
    @FXML
    private Label circuitNumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       //Ningún grupo seleccionado de base
        sesionComboBox.setPromptText("Seleccione 1º un grupo");
        modGrupo.setDisable(true);
        sesionComboBox.setDisable(true);
        graphButton.setDisable(true);
        
        gruposArrayList = gimnasio.getGrupos();
        gruposObs = FXCollections.observableList(gruposArrayList);
        for (int i = 0; i < gruposObs.size(); i++) {
            grupoComboBox.getItems().addAll(gruposObs.get(i).getCodigo());
        }
        
        //Llamada a actualizar sesiones
        IntervalTimer.actualizarSesiones(sesionesArrayList, gimnasio, sesionComboBox, sesionesObs);
        
        
        //Grupo seleccionado
        grupoComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {

            if (grupoComboBox.getSelectionModel().getSelectedIndex() > -1 ) {
                grupoActual = gruposArrayList.get(grupoComboBox.getSelectionModel().getSelectedIndex());
                modGrupo.setDisable(false);
                sesionComboBox.setDisable(false);
                graphButton.setDisable(false);
                
                sesionComboBox.setPromptText("Seleccione sesión");
            }

        });
        
        
        //Grupo seleccionado
        grupoComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {

            if (grupoComboBox.getSelectionModel().getSelectedIndex() > -1 ) {
                grupoActual = gruposArrayList.get(grupoComboBox.getSelectionModel().getSelectedIndex());
                modGrupo.setDisable(false);
                sesionComboBox.setDisable(false);
                graphButton.setDisable(false);
                
                sesionComboBox.setPromptText("Seleccione sesión");
            }

        });

    }

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
    private void crearSesionAct(ActionEvent event) throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearSesionFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void graphAct(ActionEvent event) throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/statsFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

    @FXML
    private void startAct(ActionEvent event) {
    }

    @FXML
    private void pauseAct(MouseEvent event) {
    }

    @FXML
    private void nextAct(ActionEvent event) {
    }

    @FXML
    private void resetAct(ActionEvent event) {
    }

    @FXML
    private void pauseAct(ActionEvent event) {
    }
    
    
}
