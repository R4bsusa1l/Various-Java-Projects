package functional.streaming.finance;

import java.util.Currency;

/**
 * Information needed to transfer money: IBAN and Currency
 */
public class BankAccount {
    public static final String RELAXED_IBAN_REGEX = "[A-Z][A-Z][0-9][0-9][A-Z0-9]{1,30}";
    private Currency currency = Currency.getInstance("CHF");
    private String iban;

    /**
     * Check if {@link #setIbanNumber(String) setIbanNumber}  will accept the given iban.
     *
     * @param iban the IBAN to check
     * @return true, if iban will be accepted
     */
    public static boolean isIbanAccepted(String iban) {
        return removeSpaces(iban).matches(RELAXED_IBAN_REGEX);
    }

    private static String removeSpaces(String in) {
        return in.replace(" ", "");
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * Set the BankAccounts currency. The default is CHF.
     *
     * @param currency must not be null
     * @return current instance for fluent configuration
     */
    public BankAccount setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public String getIbanNumber() {
        return iban;
    }

    /**
     * Set the IBAN. Check if the given IBAN is acceptable, see {@link #isIbanAccepted(String)}.
     *
     * @param iban must be acceptable, see {@link #isIbanAccepted(String)}
     * @return current instance for fluent configuration
     * @throws IllegalIbanNumber if iban cannot be accepted
     */
    public BankAccount setIbanNumber(String iban) throws IllegalIbanNumber {
        if (isIbanAccepted(iban)) {
            this.iban = iban;
        } else {
            throw new IllegalArgumentException("IBAN is not accepted");
        }
        return this;
    }

    @Override
    public String toString() {
        return "BankAccount{ currency=" + currency + ", iban='" + iban + "'}";
    }
}
