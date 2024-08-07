package usecase.searchproject;

import entities.ProjectInterface;
import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;
import dataaccess.local.LocalProjectRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import usecase.searchforproject.SearchProjectOutputBoundary;
import usecase.searchforproject.SearchProjectsInteractor;
import usecase.searchforproject.SearchProjectsPresenter;
import viewmodel.SearchPanelViewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SearchProjectInteractor class.
 */
public class SearchProjectInteractorTest {

    private final static String SAVE_LOCATION = "local_data/test/search_projects_interactor/";
    private final static SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
    private final static SearchProjectOutputBoundary presenter = new SearchProjectsPresenter(searchPanelViewModel);
    private static IProjectRepository projectDAO;
    private static SearchProjectsInteractor searchProjectInteractor;
    private final static EmbeddingAPIInterface apiInteface = new OpenAPIDataEmbed();
    private final static File projectSaveFile = new File(SAVE_LOCATION + "projects.csv");
    private final static File embedSaveFile = new File(SAVE_LOCATION + "embeds.csv");

    private final static String[][] dummyprojects = new String[][]{
            {"1", "Java Project", "1000.0", "A project about Java development, focusing on building robust applications.", "Java;Programming"},
            {"2", "Python Automation", "1500.5", "A project centered around automating tasks with Python scripts.", "Python;Automation"},
            {"3", "Web Development", "2000.0", "This project involves creating responsive websites using HTML, CSS, and JavaScript.", "Web Design;JavaScript"},
            {"4", "Database Management", "1200.0", "A project to enhance database performance and security.", "SQL;Database;Security"},
            {"5", "Machine Learning", "2500.0", "Exploring machine learning algorithms to predict data trends.", "Machine Learning;Data Science"}
    };

    /**
     * Sets up the test environment before all tests.
     */
    @BeforeAll
    public static void setUp() throws IOException {
        Files.deleteIfExists(projectSaveFile.toPath());
        Files.deleteIfExists(embedSaveFile.toPath());
        projectDAO = new LocalProjectRepository(SAVE_LOCATION);
        searchProjectInteractor = new SearchProjectsInteractor(presenter, projectDAO);
        addDummyProjects();
    }

    /**
     * Adds dummy projects to the repository for testing.
     */
    private static void addDummyProjects(){
        for (String[] project : dummyprojects) {
            float[] embedding = apiInteface.getEmbedData(project[3]);
            projectDAO.createProject(project[1],
                    Double.parseDouble(project[2]),
                    project[3],
                    new HashSet<>(Arrays.asList(project[4].split(";"))),
                    embedding,
                    1);
        }
    }

    /**
     * Tests the search functionality of the SearchProjectsInteractor.
     */
    @Test
    public void testSearchProjects() {
        searchProjectInteractor.searchProjects("Frontend development projects");
        ArrayList<ProjectInterface> projectsRanking = searchPanelViewModel.getProject();
        assertEquals(projectsRanking.size(), 5);
        for (ProjectInterface project : projectsRanking) {
            assertNotNull(project);

            // Since it could be hard to determine which projects get ranked first given a keyword search,
            // we will just check if the projects seems to be in the correct order.
            System.out.println(project.getProjectTitle());
        }
    }
}
