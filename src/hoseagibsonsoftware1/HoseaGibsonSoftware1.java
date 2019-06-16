/*
 * Main application hosea Gibson Software 1
 * This app is a Inventory Systems App where user can add update delete search parts
 * that are added to a GUI system
 * 
 * @author Hosea Gibson
 * email: Hgibs13@wgu.edu
 * C482 Software 1 Project 
 */
package hoseagibsonsoftware1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hosea
 */
public class HoseaGibsonSoftware1 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
