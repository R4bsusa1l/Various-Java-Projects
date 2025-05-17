package functional.streaming.humanresource;

import java.util.StringJoiner;
import java.util.UUID;

/**
 * Information about a person.
 */
public class Person {
    private final String firstName;
    private final String lastName;
    private boolean isFemale;
    private boolean isAlive = true;
    private UUID uuid;
    private Person father;
    private Person mother;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        constructNonFinalFields();
    }

    private void constructNonFinalFields() {
        this.uuid = UUID.randomUUID();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Set if the person is alive or not.
     *
     * @param alive set living status (true = alive, false = dead)
     * @return current person instance for fluent configuration
     */
    public Person setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public Person getFather() {
        return father;
    }

    /**
     * Set the father of the person.
     *
     * @param father, father of the person, can be {@code null}
     * @return current person instance for fluent configuration
     */
    public Person setFather(Person father) {
        this.father = father;
        return this;
    }

    public Person getMother() {
        return mother;
    }

    /**
     * Set the mother of the person.
     *
     * @param mother of the person, can be {@code null}
     * @return current person instance for fluent configuration
     */
    public Person setMother(Person mother) {
        this.mother = mother;
        return this;
    }

    /**
     * @return the persons' name, never {@code null}
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    public boolean isFemale() {
        return isFemale;
    }

    /**
     * Set if the Person is female or not.
     *
     * @param female true if person is female
     * @return current person instance for fluent configuration
     */
    public Person setFemale(boolean female) {
        isFemale = female;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("isFemale=" + isFemale)
            .add("isAlive=" + isAlive)
            .add("uuid=" + uuid)
            .add("father=" + father)
            .add("mother=" + mother)
            .toString();
    }

}
