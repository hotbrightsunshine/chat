package it.fi.meucci;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Inet4Address;

/**
 * JavaFX App
 */
public class App extends Application {

    public static final int PORT = 7777;
    public static final Inet4Address ADDRESS = (Inet4Address) Inet4Address.getLoopbackAddress();

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        try {
            Client c = new Client(ADDRESS, PORT);
        } catch (ConnectException e) {
            System.out.println("Connessione rifiutata. ");
        }

        launch();
        System.out.println("CIAO");
        return;
    }

}