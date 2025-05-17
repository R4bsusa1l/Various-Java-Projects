package functional.streaming.finance;

import functional.streaming.Company;
import functional.streaming.humanresource.Employee;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * This Class creates a Payroll (Lohnabrechnung) for a whole Company
 * and supplies some Utility Methods for a Payroll.
 * ✅ This class should be worked on by students.
 */
public class PayrollCreator {
    private final Company company;

    /**
     * Opens a Payroll for a company.
     *
     * @param company
     */
    public PayrollCreator(Company company) {
        this.company = company;
    }

    /*
     * Aufgabe d) - Test dazu existiert in PayrollCreatorTest
     */
    public Payroll getPayrollForAll() {
        Payroll payroll = new Payroll();
        payroll.addPayments(company.getPayments(Employee::isWorkingForCompany));
        return payroll;
    }

    /*
     * Aufgabe e) - Test dazu existiert in PayrollCreatorTest
     */
    public static int payrollValueCHF(Payroll payroll) {
        return payroll.stream().map(x -> CurrencyChange.getInNewCurrency(x.getCurrencyAmount(), CurrencyAmount.CHF)).mapToInt(CurrencyAmount::getAmount).sum();
    }

    /*
     * Aufgabe f) - schreiben Sie einen eigenen Test in PayrollCreatorTestStudent
     * @return a List of total amounts in this currency for each currency in the payroll
     */
    public static List<CurrencyAmount> payrollAmountByCurrency(Payroll payroll) {
        Map<Currency, Integer> sumByCurrency = payroll.stream()
            .collect(Collectors.groupingBy(
                payment -> payment.getCurrencyAmount().getCurrency(),
                Collectors.summingInt(payment -> payment.getCurrencyAmount().getAmount())
            ));

        List<CurrencyAmount> result = new ArrayList<>();
        for (Map.Entry<Currency, Integer> entry : sumByCurrency.entrySet()) {
            result.add(new CurrencyAmount(entry.getValue(), entry.getKey()));
        }
        return result;
    }


}
