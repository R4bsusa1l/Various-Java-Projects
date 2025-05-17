package functional.streaming;

import functional.streaming.finance.CurrencyAmount;
import functional.streaming.finance.Payment;
import functional.streaming.finance.PaymentsPerYear;
import functional.streaming.humanresource.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class models a Company with all its Employees.
 * There might be Employees not working for the Company (e.g., temporally)
 * ✅ This class should be worked on by students.
 */
public class Company {
    private final List<Employee> employeeList;

    public Company(List<Employee> employeeList) {
        Objects.requireNonNull(employeeList);
        this.employeeList = employeeList;
    }

    /**
     * This method is provided by lecturer - do not change
     * Getter for all employees.
     *
     * @return List of employees, never {@code null}
     */
    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(employeeList);
    }

    /*
     * Aufgabe a1)
     */
    public List<String> getDistinctFirstnamesOfEmployees() {
        List<String> distinctFirstName = new ArrayList<>();
        getAllEmployees().stream().distinct().forEach((x) -> distinctFirstName.add(x.getFirstName()));
        return distinctFirstName;
    }

    /*
     * Aufgabe a2)
     */
    public String[] getDistinctLastnamesOfEmployees() {
        String[] distinctLastName = {};
        List<String> tempLastName = new ArrayList<>();
        employeeList.forEach((x) -> tempLastName.add(x.getLastName()));
        distinctLastName = (String[]) tempLastName.stream().distinct().toArray();
        return distinctLastName;
    }

    /*
     * Aufgabe b)
     * There might be Employees not working for the Company (e.g., temporally)
     */
    public List<Employee> getEmployeesWorkingForCompany() {
        return getAllEmployees().stream().filter(Employee::isWorkingForCompany).toList() ;
    }

    /*
     * Aufgabe c) - Test in Klasse CompanyTestStudent
     */
    public List<Employee> getEmployeesByPredicate(Predicate<Employee> filterPredicate) {
        return getAllEmployees().stream().filter(filterPredicate).toList();
    }

    /**
     * This method is provided by lecturer - do not change
     * Create List of payments for employees which are selected by the employeePredicate
     *
     * @param employeePredicate Predicate-Function that returns true for all Employee that gets a payment
     * @return list of Payments
     */
    public List<Payment> getPayments(Predicate<Employee> employeePredicate) {
        List<Payment> paymentList = new ArrayList<>();
        // This is (intentionally) not a good functional stream because it has side effects by adding to paymentList
        // It is equivalent to a for-each loop `for (Employee employee : employeeList) { ... }`
        employeeList.forEach(employee -> {
            if (employeePredicate.test(employee)) {
                Payment payment = new Payment();
                CurrencyAmount salary = employee.getYearlySalary();
                int paymentsPerYear = employee.getPaymentsPerYear().getValue();
                salary = salary.createModifiedAmount(amount -> amount / paymentsPerYear);
                payment.setCurrencyAmount(salary).setBeneficiary(employee).setTargetAccount(employee.getAccount());
                paymentList.add(payment);
            }
        });
        return paymentList;
    }

    /*
     * Aufgabe g1)
     *
     * This Method calculates a List of Payments using a (delegate) Function.
     * @param employeePredicate - predicate for Employees eligible for a Payments
     * @param paymentForEmployee - (delegate) Function Calculating a Payment for an Employee
     * @return a List of Payments based on predicate and payment function
     */
    public List<Payment> getPayments(Predicate<Employee> employeePredicate, Function<Employee, Payment> paymentForEmployee) {
        return employeeList.stream()
            .filter(employeePredicate)
            .map(paymentForEmployee)
            .toList();
    }


    /*
     * Aufgabe g2)
     *
     * Function calculating monthly Payment
     * - the same all months for Employees having 12 Payments per year.
     * - the same for January to November for Employees having 13 Payments per year.
     */
    public static final Function<Employee, Payment> paymentForEmployeeMonthly = employee -> {
        CurrencyAmount monthlySalary = calculateMonthlySalary(employee);
        return new Payment()
            .setCurrencyAmount(monthlySalary)
            .setBeneficiary(employee)
            .setTargetAccount(employee.getAccount());
    };

    /*
     * Aufgabe g3)
     *
     * Function calculating Payment for December, where Employees having 13 Payments per year will get the double amount.
     */
    public static final Function<Employee, Payment> paymentForEmployeeDecember = employee -> {
        CurrencyAmount monthlySalary = calculateMonthlySalary(employee);
        CurrencyAmount decemberSalary = (employee.getPaymentsPerYear() == PaymentsPerYear.THIRTEEN)
            ? monthlySalary.createModifiedAmount(amount -> amount * 2)
            : monthlySalary;
        return new Payment()
            .setCurrencyAmount(decemberSalary)
            .setBeneficiary(employee)
            .setTargetAccount(employee.getAccount());
    };

    private static CurrencyAmount calculateMonthlySalary(Employee employee) {
        CurrencyAmount yearlySalary = employee.getYearlySalary();
        int paymentsPerYear = employee.getPaymentsPerYear().getValue();
        return yearlySalary.createModifiedAmount(amount -> amount / paymentsPerYear);
    }
}
