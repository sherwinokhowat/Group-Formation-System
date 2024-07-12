package usecase.createuser;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to creating a user.
 */
public class CreateUserInputData {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final double desiredCompensation;
    private final HashSet<String> tags;

    /**
     * Constructs a CreateUserInputData object with the specified details.
     *
     * @param firstName            the first name of the user.
     * @param lastName             the last name of the user.
     * @param email                the email of the user.
     * @param desiredCompensation  the desired compensation of the user.
     * @param tags                 a set of tags associated with the user.
     */
    public CreateUserInputData(String firstName, String lastName, String email, double desiredCompensation, HashSet<String> tags) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the desired compensation of the user.
     *
     * @return the desired compensation.
     */
    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    public HashSet<String> getTags() {
        return tags;
    }
}