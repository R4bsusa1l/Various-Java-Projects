package functional.streaming.finance;

import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test class is for all test methods written by students for easier review by lecturers.
 * In a real application, these tests would be in the class PayrollCreatorTest.
 * <p>
 * ✅ This class should be worked on by students.
 */
public class PayrollCreatorTestStudent {

    @Test
    void testPayrollAmountByCurrency_positive() {
        // Arrange
        Payroll payroll = mock(Payroll.class);

        Payment payment1 = mock(Payment.class);
        CurrencyAmount amount1 = new CurrencyAmount(1000, Currency.getInstance("CHF"));
        when(payment1.getCurrencyAmount()).thenReturn(amount1);

        Payment payment2 = mock(Payment.class);
        CurrencyAmount amount2 = new CurrencyAmount(500, Currency.getInstance("CHF"));
        when(payment2.getCurrencyAmount()).thenReturn(amount2);

        Payment payment3 = mock(Payment.class);
        CurrencyAmount amount3 = new CurrencyAmount(200, Currency.getInstance("USD"));
        when(payment3.getCurrencyAmount()).thenReturn(amount3);

        Payment payment4 = mock(Payment.class);
        CurrencyAmount amount4 = new CurrencyAmount(300, Currency.getInstance("EUR"));
        when(payment4.getCurrencyAmount()).thenReturn(amount4);

        List<Payment> payments = List.of(payment1, payment2, payment3, payment4);
        when(payroll.stream()).thenReturn(payments.stream());

        // Act
        List<CurrencyAmount> amountsByCurrency = PayrollCreator.payrollAmountByCurrency(payroll);

        // Assert
        assertEquals(3, amountsByCurrency.size());

        // Check for CHF
        amountsByCurrency.stream()
            .filter(ca -> ca.getCurrency().equals(Currency.getInstance("CHF")))
            .findFirst()
            .ifPresentOrElse(
                chfAmount -> assertEquals(1500, chfAmount.getAmount()),
                () -> org.junit.jupiter.api.Assertions.fail("CHF currency amount not found")
            );

        // Check for USD
        amountsByCurrency.stream()
            .filter(ca -> ca.getCurrency().equals(Currency.getInstance("USD")))
            .findFirst()
            .ifPresentOrElse(
                usdAmount -> assertEquals(200, usdAmount.getAmount()),
                () -> org.junit.jupiter.api.Assertions.fail("USD currency amount not found")
            );

        // Check for EUR
        amountsByCurrency.stream()
            .filter(ca -> ca.getCurrency().equals(Currency.getInstance("EUR")))
            .findFirst()
            .ifPresentOrElse(
                eurAmount -> assertEquals(300, eurAmount.getAmount()),
                () -> org.junit.jupiter.api.Assertions.fail("EUR currency amount not found")
            );
    }
}
