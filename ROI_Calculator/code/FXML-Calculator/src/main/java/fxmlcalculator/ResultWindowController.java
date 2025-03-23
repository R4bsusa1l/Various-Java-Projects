package fxmlcalculator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 * Controller for the MainWindow. One controller per mask (or FXML file)
 * Contains everything the controller has to reach in the view (controls)
 * and all methods the view calls based on events.
 *
 * @author ZHAW PROG2
 * @version 2024
 */
public class ResultWindowController {

    public BorderPane mainPane;
    @FXML
    private TextArea results;

    public void setValueHandler(ValueHandler valueHandler) {
        // Set initial value
        results.textProperty().setValue(valueHandler.getResult());

        // Add listener for future changes
        valueHandler.resultProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                results.textProperty().setValue(valueHandler.getResult());
            }
        });
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }
}
