/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import java.time.Duration;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Marcos
 */
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
                        tiempo.setValue(String.format("%02d", 0) + ":" + String.format("%02d", 0) + ":" + String.format("%02d", 0));
                    });
                    return true;
                } else {
                    Platform.runLater(() -> {
                        tiempo.setValue(String.format("%02d", minutos) + ":" + String.format("%02d", segundos) + ":" + String.format("%02d", centesimas));
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
                    tiempo.setValue(String.format("%02d", minutos) + ":" + String.format("%02d", segundos) + ":" + String.format("%02d", centesimas));
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
