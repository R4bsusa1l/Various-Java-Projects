package fxmlcalculator;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the MainWindow. One controller per mask (or FXML file)
 * Contains everything the controller has to reach in the view (controls)
 * and all methods the view calls based on events.
 *
 * @author ZHAW PROG2
 * @version 2024
 */
public class MainWindowController {

    @FXML
    public TextArea results;
    @FXML
    public VBox resultsView;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public CheckMenuItem initialAmountCheck;
    @FXML
    public CheckMenuItem returnInPercentCheck;
    @FXML
    public CheckMenuItem annualCostsCheck;
    @FXML
    public CheckMenuItem numberOfYearsCheck;
    @FXML
    public TextField initialAmount;
    @FXML
    public TextField returnRateInPercent;
    @FXML
    public TextField annualCost;
    @FXML
    public TextField numberOfYears;


    private ValueHandler valueHandler;

    public void setValueHandler(ValueHandler valueHandler) {
        this.valueHandler = valueHandler;
        valueHandler.resultProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                results.textProperty().setValue(valueHandler.getResult());
            }
        });
    }

    public void initialize() {
        mainPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                resultsView.setPrefWidth((double) newValue);
            }
        });

        //resultsView.widthProperty().bind(mainPane.widthProperty());

        mainPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                resultsView.setPrefHeight((double) newValue);
            }
        });
        mainPane.requestFocus(); //request focus on mainPane so F1 key will work
        mainPane.setOnMouseClicked(event -> mainPane.requestFocus());
    }

    @FXML
    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void calculate(ActionEvent actionEvent) {
        valueHandler.validateAndSetValues(initialAmount.getText(),
            returnRateInPercent.getText(), annualCost.getText(), numberOfYears.getText());

        // Update the visual feedback based on validation state
        if (valueHandler.getValuesState() == ValueHandler.ValuesState.OK) {
            results.setStyle("-fx-border-color: green;");
        } else {
            results.setStyle("-fx-border-color: red;");
        }
    }

    @FXML
    public void clearValues(ActionEvent actionEvent) {
        if (initialAmountCheck.isSelected()) initialAmount.clear();
        if (returnInPercentCheck.isSelected()) returnRateInPercent.clear();
        if (annualCostsCheck.isSelected()) annualCost.clear();
        if (numberOfYearsCheck.isSelected()) numberOfYears.clear();
    }

    @FXML
    public void clearResults(ActionEvent actionEvent) {
        valueHandler.clearResult();
        results.setStyle("");  // Reset any styling (like blue border from help)
    }

    @FXML
    public void showResultsInNewWindow(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultWindow.fxml"));
            root = loader.load();
            ResultWindowController controller = loader.getController();
            controller.setValueHandler(valueHandler);
            Stage stage = new Stage();
            stage.setTitle("results");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

            //((Node)(actionEvent.getSource())).getScene().getWindow().hide(); --> Wenn man nach wie vor den main window ausblenden will.
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showHelp() {
        // Set blue border for help text
        results.setStyle("-fx-border-color: blue;");

        // Display help text
        valueHandler.setResult("""
                Enter valid values to
                - Initial amount (> 0)
                - Return in % (can be +/- or 0)
                - Annual Costs (> 0)
                - Number of years (> 0)
                Calculate displays the annual balance development!""");
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.F2) {
            showHelp();
            keyEvent.consume(); // Consume the event
        }
    }
}
