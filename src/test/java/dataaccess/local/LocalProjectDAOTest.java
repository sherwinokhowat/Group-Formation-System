package dataaccess.local;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the LocalProjectDAO class.
 */
@SuppressWarnings("FieldCanBeLocal")
public class LocalProjectDAOTest {
    private final static String SAVE_LOCATION = "local_data/test/data_access/local_dao/";
    private static IProjectRepository projectRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "projects.csv");
    private static ILocalEmbedRepository embedRepository;
    private static EmbeddingAPIInterface apiInterface;

    /**
     * Sets up the test environment before each test.
     *
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    public void setUpEach() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        apiInterface = mock(OpenAPIDataEmbed.class);
        embedRepository = new LocalEmbedRepository(SAVE_LOCATION, apiInterface);
        projectRepository = new LocalProjectRepository(SAVE_LOCATION, embedRepository);
        projectRepository.createProject("Test Project",
                1000.0, "This is a test project.",
                new HashSet<>(Arrays.asList("Java", "Programming")),
                new float[]{0.1f, 0.2f, 0.3f, 0.4f, 0.5f},
                10);
    }

    /**
     * Tests the retrieval of a project by its ID.
     */
    @Test
    public void testGetProject() {
        assertEquals("Test Project",projectRepository.getProjectById(1).getProjectTitle());
    }

    /**
     * Tests adding tags to a project.
     */
    @Test
    public void testAddTags(){
        projectRepository.addTags(1, new HashSet<>(List.of("New Tag")));
        assertEquals(projectRepository.getProjectById(1).getProjectTags(), new HashSet<>(Arrays.asList("Java", "Programming", "New Tag")));
    }

    /**
     * Tests removing tags from a project.
     */
    @Test
    public void testRemoveTags(){
        projectRepository.removeTags(1, new HashSet<>(List.of("Java")));
        assertEquals(new HashSet<>(List.of("Programming")), projectRepository.getProjectById(1).getProjectTags());
    }

    /**
     * Tests deleting a project.
     */
    @Test
    public void testDeleteProject(){
        projectRepository.deleteProject(1);
        assertNull(projectRepository.getProjectById(1));
    }

    /**
     * Tests updating a project's details.
     */
    @Test
    public void testUpdateProject(){
        projectRepository.update(1, "Test Project 2", 2000.0, "This is a test project 2.",
                new HashSet<>(Arrays.asList("Python", "Coding")),
                new float[]{0.6f, 0.7f, 0.8f, 0.9f, 1.0f});
        assertEquals(projectRepository.getProjectById(1).getProjectTitle(), "Test Project 2");
        assertEquals(projectRepository.getProjectById(1).getProjectBudget(), 2000.0);
        assertEquals(projectRepository.getProjectById(1).getProjectDescription(), "This is a test project 2.");
        assertEquals(projectRepository.getProjectById(1).getProjectTags(), new HashSet<>(Arrays.asList("Python", "Coding")));
        assertArrayEquals(projectRepository.getAllEmbeddings().get(1), new float[]{0.6f, 0.7f, 0.8f, 0.9f, 1.0f});
    }

    /**
     * Tests retrieving the owner ID of a project.
     */
    @Test
    public void testGetOwner(){
        assertEquals(10, projectRepository.getOwnerId(1));
    }

    /**
     * Test reading from the CSV file.
     */
    @Test
    public void testReadFromCSV() {
        IProjectRepository testRepository = new LocalProjectRepository(SAVE_LOCATION, embedRepository);
        assertEquals("Test Project", testRepository.getProjectById(1).getProjectTitle());
        assertEquals(10, testRepository.getOwnerId(1));
    }

    /**
     * Test get projects by keyword.
     */
    @Test
    public void testGetProjectsByKeyword() {
        assertEquals(1, projectRepository.getProjectsByKeyword("Test Project").size());
        assertEquals(0, projectRepository.getProjectsByKeyword("Cool Project").size());
    }

}