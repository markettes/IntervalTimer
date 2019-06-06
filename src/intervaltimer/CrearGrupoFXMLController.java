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
import static intervaltimer.mainFXMLController.grupoActual;
import static intervaltimer.mainFXMLController.modificarpressed;
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
import javafx.scene.control.Label;
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
    Grupo nuevoGrupo;
    AccesoBD database = AccesoBD.getInstance();
    Gym gimnasio = database.getGym();
    ObservableList<Grupo> gruposObs;
    ArrayList<Grupo> gruposArrayList;
    @FXML
    private Label crearmodificargrupolabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sesionComboBox.setPromptText("Seleccione 1º un grupo");
        modGrupo.setDisable(true);
        sesionComboBox.setDisable(true);

        gruposArrayList = database.getGym().getGrupos();
        gruposObs = FXCollections.observableList(gruposArrayList);

        grupoComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldVal, newVal) -> {
            if (grupoComboBox.getSelectionModel().getSelectedIndex() > 0) {
                sesionComboBox.setDisable(false);
                modGrupo.setDisable(false);
                sesionComboBox.setPromptText("Seleccione sesión");

            }
        });

        if (modificarpressed == true) {
            crearmodificargrupolabel.setText("Modificar Grupo");
            codgrupoTextField.setText(mainFXMLController.grupoActual.getCodigo());
            descTextArea.setText(mainFXMLController.grupoActual.getDescripcion());

            for (int i = 0; i < gruposObs.size(); i++) {
                grupoComboBox.getItems().addAll("Grupo " + gruposObs.get(i).getCodigo());
            }

        } else {
            crearmodificargrupolabel.setText("Crear Grupo");
            for (int i = 0; i < gruposObs.size(); i++) {
                grupoComboBox.getItems().addAll("Grupo " + gruposObs.get(i).getCodigo());
            }

        }

    }

    @FXML
    private void crearGrupoAct(ActionEvent event) throws IOException {
        modificarpressed = false;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
        anchorPane.getChildren().setAll(pane);

    }

    @FXML
    private void modGrupoAct(ActionEvent event) throws IOException {
        if (modificarpressed == true) {
            grupoActual = gruposArrayList.get(grupoComboBox.getSelectionModel().getSelectedIndex());
            codgrupoTextField.setText(mainFXMLController.grupoActual.getCodigo());
            descTextArea.setText(mainFXMLController.grupoActual.getDescripcion());
        } else {
            modificarpressed = true;
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
            anchorPane.getChildren().setAll(pane);
        }

    }

    @FXML
    private void crearSesionAct(ActionEvent event) {
    }

    @FXML
    private void modSesionAct(ActionEvent event) {
    }

    @FXML
    private void guardarGrupoAct(ActionEvent event) throws IOException {

        if (codgrupoTextField.getText() == null || codgrupoTextField.getText().trim().isEmpty()) {
            Alert alertCodeGroup = new Alert(Alert.AlertType.ERROR);
            alertCodeGroup.setTitle("Faltan datos");
            alertCodeGroup.setHeaderText("Por favor, introduce un código de grupo");
            alertCodeGroup.setContentText("No se puede guardar el grupo si no introduce un código de grupo numérico válido");
            alertCodeGroup.showAndWait();
        } else if (descTextArea.getText() == null || descTextArea.getText().trim().isEmpty()) {
            Alert alertDescGroup = new Alert(Alert.AlertType.INFORMATION);
            alertDescGroup.setTitle("Faltan datos");
            alertDescGroup.setHeaderText("Por favor, introduce una descripción");
            alertDescGroup.setContentText("No se puede guardar el grupo si no introduce una descripción");
            alertDescGroup.showAndWait();
        } else {
            if (modificarpressed == false) {
                nuevoGrupo = new Grupo();
                nuevoGrupo.setCodigo(codgrupoTextField.getText());
                nuevoGrupo.setDescripcion(descTextArea.getText());
                gruposArrayList.add(nuevoGrupo);
                gimnasio.setGrupos(database.getGym().getGrupos());
            } else {
                grupoActual.setCodigo(codgrupoTextField.getText());
                grupoActual.setDescripcion(descTextArea.getText());

            }
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
