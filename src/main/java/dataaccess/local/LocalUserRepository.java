package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import config.DataAccessConfig;
import dataaccess.IUserRepository;
import entities.User;
import entities.UserInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Local implementation of the IUserRepository interface.
 * Manages user data using CSV files for storage.
 */
public class LocalUserRepository implements IUserRepository {
    private final String FILE_PATH;
    private final String[] header = {"userID", "userEmail", "userFirstName", "userLastName", "userTags", "userDesiredCompensation", "userPassword"};
    private final HashMap<Integer, UserInterface> users = new HashMap<>();
    private final HashMap<Integer, String> userPasswords = new HashMap<>();
    private int maxId = 0;

    /**
     * Constructs a LocalUserRepository with the specified file path.
     *
     * @param path the path to the directory where the CSV file is stored
     */
    public LocalUserRepository(String path) {
        FILE_PATH = path + "users.csv";
        File f = new File(FILE_PATH);
        try {
            Files.createDirectories(f.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(f.exists() && !f.isDirectory()) {
            readFromCSV();
            System.out.println("Read " + users.size() + " users from CSV");
        }
    }

    /**
     * Creates a new user and saves it to the CSV file.
     *
     * @param email the email of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param tags the tags associated with the user
     * @param desiredCompensation the desired compensation of the user
     * @param password the password of the user
     * @return the created User object
     */
    @Override
    public User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password) {
        User user = new User(maxId + 1, firstName, lastName, email, tags, desiredCompensation);
        users.put(user.getUserId(), user);
        userPasswords.put(user.getUserId(), password);
        saveToCSV();
        maxId++;
        return user;
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the User object, or null if no user is found
     */
    @Override
    public User getUserByEmail(String email) {
        for (UserInterface user : users.values()) {
            if (user.getUserEmail().equals(email)) {
                return (User) user;
            }
        }
        return null;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user
     * @return the User object, or null if no user is found
     */
    @Override
    public User getUserById(int userId) {
        if (users.containsKey(userId)) {
            return (User) users.get(userId);
        }
        return null;
    }

    /**
     * Updates a user's information.
     *
     * @param userId the ID of the user
     * @param firstName the new first name of the user
     * @param lastName the new last name of the user
     * @param desiredCompensation the new desired compensation of the user
     * @param tags the new tags of the user
     * @return false (method not implemented)
     */
    @Override
    public boolean updateUser(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        UserInterface changeUser = users.get(userId);
        User user = new User(userId, firstName, lastName, changeUser.getUserEmail(), new HashSet<>(tags), desiredCompensation);
        users.remove(userId);
        users.put(userId, user);
        saveToCSV();
        return true;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to be deleted
     * @return true if the user was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteUser(int userId) {
        users.remove(userId);
        saveToCSV();
        return true;
    }

    /**
     * Adds tags to a user.
     *
     * @param userId the ID of the user
     * @param tags the tags to be added
     * @return true if the tags were successfully added, false otherwise
     */
    @Override
    public boolean addTags(int userId, HashSet<String> tags) {
        UserInterface user = users.get(userId);
        HashSet<String> currentTags = user.getTags();
        currentTags.addAll(tags);
        user.setTags(currentTags);
        saveToCSV();
        return true;
    }

    /**
     * Removes tags from a user.
     *
     * @param userId the ID of the user
     * @param tags the tags to be removed
     * @return true if the tags were successfully removed, false otherwise
     */
    @Override
    public boolean removeTags(int userId, HashSet<String> tags) {
        UserInterface user = users.get(userId);
        HashSet<String> currentTags = user.getTags();
        currentTags.removeAll(tags);
        user.setTags(currentTags);
        saveToCSV();
        return true;
    }

    /**
     * Retrieves the password of a user by their email.
     *
     * @param email the email of the user
     * @return the password of the user, or null if no user is found
     */
    @Override
    public String getPasswordByEmail(String email) {
        User user = getUserByEmail(email);
        if (user == null) {
            return null;
        }
        return userPasswords.get(user.getUserId());
    }

    /**
     * Converts a UserInterface object to a String array for CSV writing.
     *
     * @param user the UserInterface object to convert
     * @return a String array representing the user
     */
    private String[] userToString(UserInterface user) {
        String[] row = new String[header.length];
        row[0] = String.valueOf(user.getUserId());
        row[1] = user.getUserEmail();
        row[2] = user.getFirstName();
        row[3] = user.getLastName();
        row[4] = String.valueOf(user.getTags());
        row[5] = String.valueOf(user.getDesiredCompensation());
        row[6] = userPasswords.get(user.getUserId());
        return row;
    }

    /**
     * Saves the users to a CSV file.
     */
    private void saveToCSV() {
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.writeNext(header);
        for (UserInterface user : users.values()) {
            String[] row = userToString(user);
            writer.writeNext(row);
        }
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the users from a CSV file.
     */
    private void readFromCSV() {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] line;
        try {
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                int userId = Integer.parseInt(line[0]);
                String userEmail = line[1];
                String firstName = line[2];
                String lastName = line[3];
                HashSet<String> tags = new HashSet<>(Arrays.asList(line[4].replace("[", "").replace("]", "").split(",")));
                double desiredCompensation = Double.parseDouble(line[5]);
                String password = line[6];
                UserInterface user = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
                users.put(userId, user);
                userPasswords.put(userId, password);
                maxId = Math.max(maxId, userId);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
