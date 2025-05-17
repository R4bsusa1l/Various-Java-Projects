package functional.streaming.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * A Payroll (Lohnabrechnung) is principally a List of Payments for the Company.
 * In this Payroll only one Payment for each beneficiary is allowed
 */
public class Payroll {
    private final List<Payment> paymentList = new ArrayList<>();

    public List<Payment> getPaymentList() {
        return Collections.unmodifiableList(paymentList);
    }

    /**
     * This Method will add more Payments to this Payroll and throw an IllegalArgumentException
     * if we already have a Payment belonging to the same Person in this Payroll.
     *
     * @param morePayments payments to add
     */
    public void addPayments(List<Payment> morePayments) {
        if (hasSameBeneficiary(morePayments)) {
            throw new IllegalArgumentException("Duplicate Beneficiary detected");
        } else {
            paymentList.addAll(morePayments);
        }
    }

    // Ending on first found match.
    private boolean hasSameBeneficiary(List<Payment> paymentListToVerify) {
        return paymentListToVerify.stream()
            .map(Payment::getBeneficiary)
            .anyMatch(beneficiary -> paymentList.stream()
                .map(Payment::getBeneficiary)
                .anyMatch(beneficiary::equals));
    }

    /**
     * Returns a stream of all Payments in this Payroll.
     *
     * @return Stream of all Payments in this Payroll
     */
    public Stream<Payment> stream() {
        return paymentList.stream();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Payroll.class.getSimpleName() + "[", "]")
            .add("paymentList=" + paymentList)
            .toString();
    }
}
