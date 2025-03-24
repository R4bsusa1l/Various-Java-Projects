package fxmlcalculator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

import static fxmlcalculator.ValueHandler.ValuesState.*;

/**
 * Handles and stores (as a View Model) the values from the input form and contains the logic to calculate the ROI (return on investment)
 * Offers the {@link #result} StringProperty to listen from a view (bind to a field in the view or add a listener)
 * The validity state of the form values is stored in the {@link #valuesState} enum.
 *
 * @author ZHAW PROG2
 * @version 2024
 */
public class ValueHandler {

    private static final int MIN_REQUIRED_YEARS = 1;
    private static final int MAX_ALLOWED_YEARS = 99;
    private static final double MIN_AMOUNT = 0.0;

    private double initialAmount;
    private double returnInPercent;
    private double annualCost;
    private int numberOfYears;

    /**
     * using a StringProperty to store the result value.
     * This allows to bind the value to a UI element or
     * add a {@link javafx.beans.value.ChangeListener} to react on changes.
     */
    private final StringProperty result = new SimpleStringProperty();

    /**
     * Enum used for the validity state of the form values
     */
    enum ValuesState {UNDEFINED, OK, ERROR}

    /**
     * validation state of the form values
     */
    private ValuesState valuesState = UNDEFINED;

    /**
     * @return the validation state of the form values
     */
    public ValuesState getValuesState() {
        return this.valuesState;
    }

    /**
     * Reset the values state to the default value (UNDEFINED)
     */
    public void resetValuesState() {
        this.valuesState = UNDEFINED;
    }

    /**
     * String containing the result of the calculation
     * Can be "", if no calculation or check is done or could contain the error message on invalid values
     *
     * @return String with the result of the value checking or the calculation
     */
    public String getResult() {
        return result.get();
    }


    /**
     * Sets the result of the calculation (or error message).
     * The result StringProperty will trigger the change listeners
     *
     * @param resultText text to be displayed in the result field
     */
    public void setResult(String resultText) {
        result.set(resultText);
    }

    /**
     * Gives access to the StringProperty holding the result of the calculation
     *
     * @return result String property which can be bound to UI elements
     */
    public StringProperty resultProperty() {
        return result;
    }

    /**
     * Check if the input values are valid and set the data fields.
     * If not ok, set valuesOK to false and return an error message.
     *
     * @param initialAmount   initial amount of the investment (must be >= 0)
     * @param returnInPercent annual return rate in percent (must be >= 0)
     * @param annualCost      annual costs e.g., fees, taxes (must be >= 0)
     * @param numberOfYears   number of years to calculate (must be between 1 and 99)
     */
    public void validateAndSetValues(String initialAmount, String returnInPercent, String annualCost, String numberOfYears) {
        this.valuesState = OK;
        StringBuilder errorMessage = new StringBuilder();
        this.initialAmount = parseNumberValue(initialAmount, MIN_AMOUNT, Double.MAX_VALUE,
            "Please specify a positive initial amount!", errorMessage)
            .orElse(0.0).doubleValue();
        this.returnInPercent = parseNumberValue(returnInPercent, MIN_AMOUNT, Double.MAX_VALUE,
            "Please specify the annual return rate in %%!", errorMessage)
            .orElse(0.0).doubleValue() / 100;
        this.annualCost = parseNumberValue(annualCost, MIN_AMOUNT, Double.MAX_VALUE,
            "Please specify the annual cost! (>= %.1f)", errorMessage)
            .orElse(0.0).doubleValue();
        this.numberOfYears = parseNumberValue(numberOfYears, MIN_REQUIRED_YEARS, MAX_ALLOWED_YEARS,
            "Please enter a time period from %d to %d years!", errorMessage)
            .orElse(0).intValue();
        setResult(this.valuesState == OK ? calculateResult() : errorMessage.toString());
    }

    /**
     * Parses a number string, check if it is in the given range and returns it as an Optional&lt;Number&gt;.
     * If it is invalid or not in the range, the valuesOk flag is set to false and the error message is added to the error message builder.
     *
     * @param numberString        the number string to parse
     * @param minValue            the minimum value of the range
     * @param maxValue            the maximum value of the range
     * @param errorMessage        the error message to add to the error message builder if the number is invalid or not in the range
     * @param errorMessageBuilder the error message builder to add the error message to
     * @return the parsed number as an Optional&lt;Number&gt; or an empty Optional if the number is invalid or not in the range
     */
    private Optional<Number> parseNumberValue(String numberString, Number minValue, Number maxValue,
                                              String errorMessage, StringBuilder errorMessageBuilder) {
        if (numberString != null && !numberString.isBlank()) {
            try {
                Number number = NumberFormat.getInstance().parse(numberString);
                if (number.doubleValue() >= minValue.doubleValue() && number.doubleValue() <= maxValue.doubleValue()) {
                    return Optional.of(number);
                }
            } catch (ParseException e) {
                // error will be reported below
            }
        }
        errorMessageBuilder.append(errorMessage.formatted(minValue, maxValue)).append(System.lineSeparator());
        this.valuesState = ERROR;
        return Optional.empty();
    }

    /**
     * Calculates the result of the investment for each year and returns it as a String.
     *
     * @return the calculated result as a String
     */
    private String calculateResult() {
        StringBuilder resultStringBuilder = new StringBuilder();
        double calculatedAmount = initialAmount;
        for (int year = 1; year <= numberOfYears; year++) {
            calculatedAmount = calculatedAmount * (1 + returnInPercent) - annualCost;
            resultStringBuilder.append("After %2d year(s): %8.2f%n".formatted(year, calculatedAmount));
        }
        return resultStringBuilder.toString();
    }

    /**
     * clears the result and resets all values
     */
    public void clearResult() {
        this.initialAmount = 0;
        this.returnInPercent = 0;
        this.annualCost = 0;
        this.numberOfYears = 0;
        this.valuesState = UNDEFINED;
        this.setResult("");
    }

}
