package functional.streaming.finance;

import java.util.Currency;
import java.util.Map;
import java.util.Objects;

/**
 * Helper class to change currency
 */
public class CurrencyChange {
    private CurrencyChange() {
        // no instances
    }

    private static final Map<String, Double> CURRENCY_ISOCODE_TO_CHF_FACTOR = Map.of(
        "CHF", 1.00,
        "USD", 1.04,
        "GBP", 0.83,
        "EUR", 0.94
    );

    private static double factorFor(String fromIsoCode, String toIsoCode) {
        return CURRENCY_ISOCODE_TO_CHF_FACTOR.get(toIsoCode) / CURRENCY_ISOCODE_TO_CHF_FACTOR.get(fromIsoCode);
    }

    /**
     * Convert to a new Currency
     * <p>
     * Example:
     * <code>
     * CurrencyAmount old = new CurrencyAmount (12345, Currency.getInstance("EUR"));
     * CurrencyAmount newCurrencyAmount = CurrencyChange.getInNewCurrency(old, Currency.getInstance("USD"));
     * </code>
     *
     * @param currencyAmount an amount in given currency
     * @param newCurrency    the target currency
     * @return new instance with an equivalent value but in the new currency
     */
    public static CurrencyAmount getInNewCurrency(CurrencyAmount currencyAmount, Currency newCurrency) {
        Objects.requireNonNull(currencyAmount);
        Objects.requireNonNull(newCurrency);
        double factor = factorFor(currencyAmount.getCurrency().getCurrencyCode(), newCurrency.getCurrencyCode());
        long newAmount = Math.round(currencyAmount.getAmount() * factor);
        return new CurrencyAmount((int) newAmount, newCurrency);
    }
}
