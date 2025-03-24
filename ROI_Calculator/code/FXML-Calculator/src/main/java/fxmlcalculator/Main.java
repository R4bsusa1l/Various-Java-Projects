package fxmlcalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main-Application. Opens the first window (MainWindow) and the common ValueHandler
 *
 * @author ZHAW PROG2
 * @version 2024
 */
public class Main extends Application {

    private final ValueHandler valueHandler = new ValueHandler();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainWindow(primaryStage);
    }

    private void mainWindow(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Pane rootNode = loader.load();
            MainWindowController controller = loader.getController();
            controller.setValueHandler(valueHandler);
            Scene scene = new Scene(rootNode);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}

