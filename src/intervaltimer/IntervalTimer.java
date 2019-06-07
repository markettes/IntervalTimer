/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intervaltimer;

import accesoBD.AccesoBD;
import com.jfoenix.controls.JFXComboBox;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Gym;
import modelo.SesionTipo;

public class IntervalTimer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/mainFXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    /*Metodo para actualizar lista de sesiones
    Entrada:    - sesionesArrayList
                - gimnasio
                - comboBox
                - sesionesObs
    */
    
    public static void actualizarSesiones(ArrayList<SesionTipo> sesionesArrayList, Gym gimnasio, JFXComboBox<String> sesionComboBox, ObservableList<SesionTipo> sesionesObs){
        sesionesArrayList = gimnasio.getTiposSesion();
        sesionesObs = FXCollections.observableList(sesionesArrayList);
        
        for (int i = 0; i < sesionesObs.size(); i++) {
            sesionComboBox.getItems().addAll(sesionesObs.get(i).getCodigo());
        }
    }
}
