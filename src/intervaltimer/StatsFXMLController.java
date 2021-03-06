/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import accesoBD.AccesoBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import static intervaltimer.mainFXMLController.grupoActual;
import static intervaltimer.mainFXMLController.modificarpressed;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
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
public class StatsFXMLController implements Initializable {

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
    Grupo nuevoGrupo;
    AccesoBD database = AccesoBD.getInstance();
    Gym gimnasio = database.getGym();
    ObservableList<Grupo> gruposObs;
    ArrayList<Grupo> gruposArrayList;
    @FXML
    private JFXButton graphButton;
    ArrayList<SesionTipo> sesionesArrayList;
    ObservableList<SesionTipo> sesionesObs;
    @FXML
    private LineChart<Integer, Integer> linechart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    ArrayList<Sesion> sesionesGrupoActual;
    @FXML
    private JFXComboBox<String> mostrarsesionesComboBox;
    ObservableList<String> sesionesDesdeObs;
    @FXML
    private CheckBox trabajoCheckBox;
    @FXML
    private CheckBox descansoCheckBox;
    @FXML
    private CheckBox realCheckBox;
    ArrayList<String> sesionesDesde;

    ArrayList<Sesion> sesionesAMostrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        grupoComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {

            if (grupoComboBox.getSelectionModel().getSelectedIndex() > -1) {
                grupoActual = gruposArrayList.get(grupoComboBox.getSelectionModel().getSelectedIndex());
                modGrupo.setDisable(false);
                sesionComboBox.setDisable(false);
                graphButton.setDisable(false);

                sesionComboBox.setPromptText("Seleccione sesión");
            }

        });

        // TODO
        sesionesAMostrar = new ArrayList<>();

        sesionesDesde = new ArrayList<>();
        sesionesDesde.add("Esta Semana");
        sesionesDesde.add("Desde hace 2 semanas");
        sesionesDesde.add("Este mes");
        sesionesDesde.add("Todas");

        sesionesDesdeObs = FXCollections.observableList(sesionesDesde);

        mostrarsesionesComboBox.getItems().addAll(sesionesDesde);
        sesionesGrupoActual = grupoActual.getSesiones();

        xAxis.setLabel("Sesiones");
        yAxis.setLabel("Tiempo");
        linechart.setTitle("Sesiones Grupo " + grupoActual.getCodigo());

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Tiempo de trabajo");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Tiempo de descanso");

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Tiempo real");

        mostrarsesionesComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (oldVal != newVal) {
                series1.getData().removeAll();
                series2.getData().removeAll();
                series3.getData().removeAll();
            }

            if (newVal.compareTo("Esta Semana") == 0) {
                for (int i = 0; i < sesionesGrupoActual.size(); i++) {
                    LocalDateTime sesionActual = sesionesGrupoActual.get(i).getFecha();
                    if (sesionActual.getDayOfYear() >= LocalDateTime.now().getDayOfYear() - 7) {
                        sesionesAMostrar.add(sesionesGrupoActual.get(i));
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series1.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                    sesionesAMostrar.get(j).getTipo().getT_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_circuitos() * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                        }
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series2.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                    sesionesAMostrar.get(j).getTipo().getD_circuito() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                    + sesionesAMostrar.get(j).getTipo().getD_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                        }
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series3.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(), (int) sesionesAMostrar.get(j).getDuracion().getSeconds()));

                        }

                    }
                }
            }

            if (newVal.compareTo("Desde hace 2 semanas") == 0) {
                for (int i = 0; i < sesionesGrupoActual.size(); i++) {
                    LocalDateTime sesionActual = sesionesGrupoActual.get(i).getFecha();
                    if (sesionActual.getDayOfYear() >= LocalDateTime.now().getDayOfYear() - 14) {
                        sesionesAMostrar.add(sesionesGrupoActual.get(i));
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series1.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                    sesionesAMostrar.get(j).getTipo().getT_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                    * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                        }
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series2.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                    sesionesAMostrar.get(j).getTipo().getD_circuito() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                    + sesionesAMostrar.get(j).getTipo().getD_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                        }
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series3.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(), (int) sesionesAMostrar.get(j).getDuracion().getSeconds()));

                        }
                    }
                }
            }

            if (newVal.compareTo("Este mes") == 0) {
                for (int i = 0; i < sesionesGrupoActual.size(); i++) {
                    LocalDateTime sesionActual = sesionesGrupoActual.get(i).getFecha();
                    if (sesionActual.getDayOfYear() >= LocalDateTime.now().getDayOfYear() - 31) {
                        sesionesAMostrar.add(sesionesGrupoActual.get(i));
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series1.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                    sesionesAMostrar.get(j).getTipo().getT_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                    * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                        }
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series2.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                    sesionesAMostrar.get(j).getTipo().getD_circuito() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                    + sesionesAMostrar.get(j).getTipo().getD_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                        }
                        for (int j = 0; j < sesionesAMostrar.size(); j++) {
                            series3.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(), (int) sesionesAMostrar.get(j).getDuracion().getSeconds()));

                        }
                    }
                }
            }

            if (newVal.compareTo("Todas") == 0) {
                for (int i = 0; i < sesionesGrupoActual.size(); i++) {
                    sesionesAMostrar.add(sesionesGrupoActual.get(i));
                    for (int j = 0; j < sesionesAMostrar.size(); j++) {
                        series1.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                sesionesAMostrar.get(j).getTipo().getT_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                    }
                    for (int j = 0; j < sesionesAMostrar.size(); j++) {
                        series2.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(),
                                sesionesAMostrar.get(j).getTipo().getD_circuito() * sesionesAMostrar.get(j).getTipo().getNum_circuitos()
                                + sesionesAMostrar.get(j).getTipo().getD_ejercicio() * sesionesAMostrar.get(j).getTipo().getNum_ejercicios()));

                    }
                    for (int j = 0; j < sesionesAMostrar.size(); j++) {
                        series3.getData().add(new XYChart.Data(sesionesAMostrar.get(j).getTipo().getCodigo(), (int) sesionesAMostrar.get(j).getDuracion().getSeconds()));

                    }

                }
            }

        });

        //listeners para que aparezcan o desaparezcan las variables en la linechart
        trabajoCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) {
                linechart.getData().addAll(series1);
            } else {
                linechart.getData().removeAll(series1);
            }

        });
        descansoCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) {
                linechart.getData().addAll(series2);
            } else {
                linechart.getData().removeAll(series2);
            }
        });
        realCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) {
                linechart.getData().addAll(series3);
            } else {
                linechart.getData().removeAll(series3);
            }
        });
        sesionesArrayList = gimnasio.getTiposSesion();
        //Llamada a actSesiones
        IntervalTimer.actualizarSesiones(sesionesArrayList, sesionComboBox, sesionesObs);

        sesionComboBox.setPromptText("Seleccione 1º un grupo");
        modGrupo.setDisable(true);
        sesionComboBox.setDisable(true);
        graphButton.setDisable(true);

        gruposArrayList = database.getGym().getGrupos();
        gruposObs = FXCollections.observableList(gruposArrayList);
        for (int i = 0; i < gruposObs.size(); i++) {
            grupoComboBox.getItems().addAll(gruposObs.get(i).getCodigo());
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
        modificarpressed = true;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/crearGrupoFXML.fxml"));
        anchorPane.getChildren().setAll(pane);

    }

    @FXML
    private void crearSesionAct(ActionEvent event) {
    }

    @FXML
    private void graphAct(ActionEvent event) {

    }

    @FXML
    private void homeAction(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/mainFXML.fxml"));
        anchorPane.getChildren().setAll(pane);
    }

}
