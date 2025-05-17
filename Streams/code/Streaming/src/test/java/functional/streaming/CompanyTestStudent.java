package functional.streaming;

import functional.streaming.humanresource.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class is for all test methods written by students for easier review by lecturers.
 * In a real application, these tests would be in the class CompanyTest.
 * <p>
 * ✅ This class should be worked on by students.
 */
class CompanyTestStudent {
    private Company testCompany;

    @BeforeEach
    void setUp() {
        Random random = new Random(CompanyTest.RANDOM_SEED);
        CompanySupplier companySupplier = new CompanySupplier(random, CompanyTest.EMPLOYEE_COUNT);
        testCompany = companySupplier.get();
    }

    /*
     * Aufgabe c)
     */
    @Test
    void getEmployeesByPredicate() {
        long femaleEmployees = testCompany.getEmployeesByPredicate(Person::isFemale).size();
        long maleEmployees = testCompany.getEmployeesByPredicate(x -> !x.isFemale()).size();
        assertEquals(testCompany.getAllEmployees().size(), (femaleEmployees + maleEmployees));
    }

}

