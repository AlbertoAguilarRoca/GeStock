package edu.gestock.gestockProyect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import edu.gestock.services.UserSession;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private static UserSession sesion;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Login"), 1200, 900);
        stage.setScene(scene);
        stage.setTitle("GeStock");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static UserSession getUserSesion() {
    	return sesion;
    }
    
    public static void setUserSesion(UserSession newSesion) {
    	sesion = newSesion;
    }

    public static void main(String[] args) {
        launch();
    }

}