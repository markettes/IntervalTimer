/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import accesoBD.AccesoBD;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
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
    private Label timeLabel;
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

    private CronoService servicio;
    private Property<Boolean> iniciado = new SimpleBooleanProperty(false);
    private boolean firstime;
    protected SesionTipo sesionTipoActual;
    @FXML
    private Label ejercLabel;
    
    File f = new File("src/images/sound.mp3");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        timeLabel.setText(String.format("%02d", 0) + ":" + String.format("%02d", 0));

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
        sesionesArrayList = gimnasio.getTiposSesion();
        //Llamada a actualizar sesiones
        IntervalTimer.actualizarSesiones(sesionesArrayList, sesionComboBox, sesionesObs);

        //Grupo seleccionado
        grupoComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {

            if (grupoComboBox.getSelectionModel().getSelectedIndex() > -1) {
                grupoActual = gruposArrayList.get(grupoComboBox.getSelectionModel().getSelectedIndex());
                modGrupo.setDisable(false);
                sesionComboBox.setDisable(false);
                graphButton.setDisable(false);

                sesionComboBox.setPromptText("Seleccione sesión");
            }

        });

        sesionComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
            if (sesionComboBox.getSelectionModel().getSelectedIndex() > -1) {
                for (int i = 0; i < sesionesArrayList.size(); i++) {
                    if (sesionesArrayList.get(i).getCodigo().compareTo(sesionComboBox.getSelectionModel().getSelectedItem()) == 0) {
                        sesionTipoActual = sesionesArrayList.get(i);
                    }

                }
            }
        });

        //CRONOMETRO
        servicio = new CronoService();
        servicio.setTiempo(timeLabel.textProperty());
        pauseButton.disableProperty().bind(Bindings.not((ObservableBooleanValue) iniciado));
        startButton.disableProperty().bind(iniciado);
        resetButton.disableProperty().bind(iniciado);
        nextButton.disableProperty().bind(iniciado);
        //servicio.setCountDown(true);

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
        ejercLabel.setText("Calentamiento");
        int tCal = sesionTipoActual.getT_calentamiento();
        if (tCal != 0) {
            servicio.setCountDown(tCal);

            servicio.start();
            iniciado.setValue(true);
            timeLabel.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.compareTo("00:00") == 0) {
                    
                    
                }

            });
        }
        for (int i = 0; i < sesionTipoActual.getNum_circuitos(); i++) {
            for (int j = 0; j < sesionTipoActual.getNum_ejercicios() * 2 - 1; j++) {

                if (j % 2 == 0) {
                    ejercLabel.setText("Ejercicio " + (i / 2 + 1));
                    int tEj = sesionTipoActual.getT_ejercicio();
                    servicio.setCountDown(tEj);
                    servicio.start();
                    iniciado.setValue(true);
                    timeLabel.textProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal.compareTo("00:00") == 0) {
                            playSong(f);
                            

                        }
                    });
                }

                if (j % 2 == 1) {
                    ejercLabel.setText("Descanso " + (i / 2 + 1));
                    int tDes = sesionTipoActual.getD_ejercicio();
                    servicio.setCountDown(tDes);
                    servicio.start();
                    iniciado.setValue(true);
                    timeLabel.textProperty().addListener((observable, oldVal, newVal) -> {
                        if (newVal.compareTo("00:00") == 0) {
                            playSong(f);
                            
                        }
                    });
                }
            }
        }
    }

    private void pauseAct(MouseEvent event) {
        servicio.cancel();
        servicio.reset();
        iniciado.setValue(false);
    }

    @FXML
    private void nextAct(ActionEvent event) {
    }

    @FXML
    private void resetAct(ActionEvent event) {
        servicio.restaurarInicio();
        firstime = true;
        timeLabel.setText("00:00");
    }

    @FXML
    private void pauseAct(ActionEvent event) {
    }
    
    
    //Sonido
    private void playSong(File file){
        Media sound = new Media(file.toURI().toString());
        MediaPlayer mediaPlayerS = new MediaPlayer(sound);
        mediaPlayerS.play();
    }

}

class CronoService extends Service<Void> {

    private static final int DELAY = 100;
    //tiempos
    private static long lastTime = 0; // guarda la hora del ultimo instante
    private static long startTime = 0;// guarda la hora del instante inicial del intervalo
    private static long stoppedTime = 0;// guarda la duracion del tiempo parados

    private boolean stopped = false;//indica si se ha parado el cronometro
    private boolean countdown = false;// indica si esta en cuenta atras
    private long countDownMilis;

    CronoService() {
        //cuenta atras de 30 segundos, deberia de ser configurable
        this.countDownMilis = 30 * 1000;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            protected Void call() throws Exception {
                if (!stopped) {
                    startTime = lastTime = System.currentTimeMillis();
                } else { // estabamos detenidos y nos ponemos en marcha sin cambio de estado
                    Long elapsedTime = System.currentTimeMillis() - lastTime;
                    stoppedTime = stoppedTime + elapsedTime;
                    stopped = false;
                }
                while (true) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException ex) {
                        if (isCancelled()) {
                            break;
                        }
                    }
                    if (isCancelled()) {
                        break;
                    }
                    if (countdown) {
                        if (calculaCountDown()) {
                            break;
                        }

                    } else {
                        calculaCountUp();
                    }
                }
                return null;
            }

            private boolean calculaCountDown() {
                lastTime = System.currentTimeMillis();
                Long totalTime = (lastTime - startTime) - stoppedTime;
                Duration duration = Duration.ofMillis(countDownMilis - totalTime);
                final long minutos = duration.toMinutes();
                final long segundos = duration.minusMinutes(minutos).getSeconds();
                final long centesimas = duration.minusSeconds(segundos).toNanos() / 10000000;

                // no se como parar en la milesima justa
                if ((segundos == 0) && (centesimas < 10)) {
                    Platform.runLater(() -> {
                        tiempo.setValue(String.format("%02d", 0) + ":" + String.format("%02d", 0));
                    });
                    return true;
                } else {
                    Platform.runLater(() -> {
                        tiempo.setValue(String.format("%02d", minutos) + ":" + String.format("%02d", segundos));
                    });
                    return false;
                }
            }

            private void calculaCountUp() {
                lastTime = System.currentTimeMillis();
                Long totalTime = (lastTime - startTime) - stoppedTime;
                Duration duration = Duration.ofMillis(totalTime);
                final Long minutos = duration.toMinutes();
                final Long segundos = duration.minusMinutes(minutos).getSeconds();
                final Long centesimas = duration.minusSeconds(segundos).toNanos() / 10000000;
                Platform.runLater(() -> {
                    tiempo.setValue(String.format("%02d", minutos) + ":" + String.format("%02d", segundos));
                });
            }
        };
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        lastTime = System.currentTimeMillis();
        stopped = true;
    }

    // propiedad donde se muestra el tiempo transcurrido
    private StringProperty tiempo = new SimpleStringProperty();

    public String getTiempo() {
        return tiempo.get();
    }

    public void setTiempo(StringProperty value) {
        tiempo = value;
    }

    public StringProperty tiempoProperty() {
        return tiempo;
    }

    public void setCountDown(boolean bol) {
        countdown = bol;
    }

    public void setCountDown(int seconds) {
        this.countDownMilis = seconds * 1000;
    }

    public void restaurarInicio() {
        lastTime = 0; // guarda la hora del ultimo instante
        startTime = 0;// guarda la hora del instante inicial del intervalo
        stoppedTime = 0;// guarda la duracion del tiempo parados

        //indica si se ha parado el cronometro
        stopped = false;
    }

}
