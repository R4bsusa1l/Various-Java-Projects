package functional.streaming.finance;

import functional.streaming.humanresource.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PayrollTest {
    private static final Person adam = new Person("Adam", "First");

    @Test
    void addPayments() {
        Payment firstPayment = new Payment().setBeneficiary(adam);
        Payroll payroll = new Payroll();
        List<Payment> paymentList = new ArrayList<>(1);
        paymentList.add(firstPayment);
        payroll.addPayments(paymentList);
        assertIterableEquals(paymentList, payroll.getPaymentList());

        paymentList.addFirst(new Payment().setBeneficiary(new Person("Eva", "Second")));
        paymentList.add(new Payment().setBeneficiary(new Person("Kaine", "Third")));

        assertThrows(IllegalArgumentException.class, () ->
            payroll.addPayments(paymentList), "detect duplicate beneficiary");
    }
}
