package use_case.create_project;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to creating a project.
 */
public class CreateProjectOutputData {
    private final int projectId;
    private final String title;
    private final double budget;
    private final String description;
    private final HashSet<String> tags;
    private final boolean success;
    /**
     * Constructs a CreateProjectOutputData object with the specified details.
     *
     * @param projectId   the ID of the created project.
     * @param title       the title of the project.
     * @param budget      the budget allocated for the project.
     * @param description a brief description of the project.
     * @param tags        a set of tags associated with the project.
     * @param success     whether the project creation was successful.
     */
    public CreateProjectOutputData(int projectId, String title, double budget, String description, HashSet<String> tags, boolean success) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = new HashSet<>(tags); // Defensive copy to ensure immutability
        this.success = success;
    }

    /**
     * Returns the ID of the created project.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Returns the title of the project.
     *
     * @return the project title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the budget allocated for the project.
     *
     * @return the project budget.
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Returns the description of the project.
     *
     * @return the project description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the set of tags associated with the project.
     *
     * @return the project tags.
     */
    public HashSet<String> getTags() {
        return new HashSet<>(tags); // Return an unmodifiable view
    }

    /**
     * Returns whether the project creation was successful.
     *
     * @return true if the project creation was successful, otherwise false.
     */
    public boolean isSuccess() {
        return success;
    }
}
