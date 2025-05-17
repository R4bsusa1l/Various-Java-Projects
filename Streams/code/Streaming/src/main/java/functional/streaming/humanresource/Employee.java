package functional.streaming.humanresource;

import functional.streaming.finance.BankAccount;
import functional.streaming.finance.CurrencyAmount;
import functional.streaming.finance.PaymentsPerYear;

import java.util.Optional;

public class Employee extends Person {
    private CurrencyAmount yearlySalary;
    private PaymentsPerYear paymentsPerYear = PaymentsPerYear.THIRTEEN;
    private BankAccount account;
    private boolean isWorkingForCompany;

    public Employee(String firstName, String lastName) {
        super(firstName, lastName);
    }

    /**
     * There might be Employees not working for the Company (e.g., temporally)
     */
    public boolean isWorkingForCompany() {
        return isWorkingForCompany;
    }

    /**
     * There might be Employees not working for the Company (e.g. temporally)
     *
     * @param workingForCompany true if the employee works for the company
     * @return current employee instance for fluent configuration
     */
    public Employee setWorkingForCompany(boolean workingForCompany) {
        isWorkingForCompany = workingForCompany;
        return this;
    }

    /**
     * Getter for the employee's yearly salary
     *
     * @return yearly salary of the employee
     */
    public CurrencyAmount getYearlySalary() {
        return yearlySalary;
    }

    /**
     * Setter for the employee's yearly salary
     *
     * @param yearlySalary yearly salary of the employee
     * @return current employee instance for fluent configuration
     */
    public Employee setYearlySalary(CurrencyAmount yearlySalary) {
        this.yearlySalary = yearlySalary;
        return this;
    }

    /**
     * Getter for the employee's number of payments per year (usually 12 or 13
     *
     * @return number of payments per year
     */
    public PaymentsPerYear getPaymentsPerYear() {
        return paymentsPerYear;
    }

    /**
     * Setter for the employee's number of payments per year (usually 12 or 13)
     *
     * @param paymentsPerYear number of employee's payments per year
     * @return current employee instance for fluent configuration
     */
    public Employee setPaymentsPerYear(PaymentsPerYear paymentsPerYear) {
        this.paymentsPerYear = paymentsPerYear;
        return this;
    }

    /**
     * Getter for the employee's bank account
     *
     * @return bank account of the employee
     */
    public Optional<BankAccount> getAccount() {
        return Optional.ofNullable(account);
    }

    /**
     * Setter for the employee's bank account
     *
     * @param account bank account of the employee
     * @return current employee instance for fluent configuration
     */
    public Employee setAccount(BankAccount account) {
        this.account = account;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("yearlySalaryCHF=").append(yearlySalary);
        sb.append(", paymentsPerYear=").append(paymentsPerYear);
        sb.append(", account=").append(account);
        sb.append(", person=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
