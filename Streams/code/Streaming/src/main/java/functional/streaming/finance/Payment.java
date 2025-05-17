package functional.streaming.finance;

import functional.streaming.humanresource.Person;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * All information needed to pay an amount to a BankAccount of a Person.
 */
public class Payment {
    private BankAccount targetAccount;
    private CurrencyAmount currencyAmount;
    private Person beneficiary;

    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    /**
     * Set the target account for the payment.
     *
     * @param targetAccount target account for the payment
     * @return current payment instance for fluent configuration
     */
    public Payment setTargetAccount(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

    /**
     * Set the target account for the payment, if present.
     *
     * @param targetAccount target account for the payment
     * @return current payment instance for fluent configuration
     */
    public Payment setTargetAccount(Optional<BankAccount> targetAccount) {
        return setTargetAccount(targetAccount.orElse(null));
    }

    public CurrencyAmount getCurrencyAmount() {
        return currencyAmount;
    }

    /**
     * Set the amount to pay.
     *
     * @param currencyAmount amount to pay in the given currency
     * @return current payment instance for fluent configuration
     */
    public Payment setCurrencyAmount(CurrencyAmount currencyAmount) {
        this.currencyAmount = currencyAmount;
        return this;
    }

    public Person getBeneficiary() {
        return beneficiary;
    }

    /**
     * Set the beneficiary of the payment.
     *
     * @param beneficiary beneficiary of the payment
     * @return current payment instance for fluent configuration
     */
    public Payment setBeneficiary(Person beneficiary) {
        this.beneficiary = beneficiary;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Payment.class.getSimpleName() + "[", "]")
            .add("targetAccount=" + targetAccount)
            .add("currencyAmount=" + currencyAmount)
            .add("beneficiary=" + beneficiary)
            .toString();
    }
}
