package usecase.searchforproject;

import entities.ProjectInterface;

import java.util.ArrayList;

/**
 * Interface for searching projects.
 */
public interface ProjectSearchInterface {

    /**
     * Searches for projects based on the given query.
     *
     * @param query  the query to search for.
     * @param amount the amount of projects to return.
     * @return the list of projects that match the query.
     */
    ArrayList<ProjectInterface> searchProjects(String query, int amount);
}
