package functional.streaming;

import functional.streaming.humanresource.Employee;
import functional.streaming.humanresource.EmployeeSupplier;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CompanySupplier implements Supplier<Company> {
    private final int employeeCount;
    private final EmployeeSupplier employeeSupplier;

    public CompanySupplier(Random random, int employeeCount) {
        Objects.requireNonNull(random);
        this.employeeCount = employeeCount;
        employeeSupplier = new EmployeeSupplier(random);
    }

    @Override
    public Company get() {
        List<Employee> employeeList = Stream.generate(employeeSupplier).limit(employeeCount).toList();
        return new Company(employeeList);
    }
}
