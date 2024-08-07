package usecase.manageprojects.editproject;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EditProjectOutputData class.
 */
public class EditProjectOutputDataTest {

    /**
     * Tests the EditProjectOutputData constructor and its getters.
     */
    @Test
    public void testEditProjectOutputData() {
        int projectId = 1;
        String title = "New Title";
        double budget = 5000.0;
        String description = "New Description";
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");

        EditProjectOutputData outputData = new EditProjectOutputData(projectId, title, budget, description, tags);

        // Verify that the data is correctly set
        assertEquals(projectId, outputData.getProjectId());
        assertEquals(title, outputData.getTitle());
        assertEquals(budget, outputData.getBudget(), 0.001);
        assertEquals(description, outputData.getDescription());
        assertEquals(tags, outputData.getTags());
        assertNotSame(tags, outputData.getTags()); // Ensure defensive copy
    }

    /**
     * Tests the immutability of the tags in EditProjectOutputData.
     */
    @Test
    public void testTagsImmutability() {
        HashSet<String> tags = new HashSet<>();
        tags.add("tag1");
        tags.add("tag2");

        EditProjectOutputData outputData = new EditProjectOutputData(1, "New Title", 5000.0, "New Description", tags);

        HashSet<String> retrievedTags = outputData.getTags();
        retrievedTags.add("tag3");

        // Verify that modifying the retrieved tags does not affect the original tags in EditProjectOutputData
        assertEquals(2, outputData.getTags().size());
        assertTrue(outputData.getTags().contains("tag1"));
        assertTrue(outputData.getTags().contains("tag2"));
        assertFalse(outputData.getTags().contains("tag3"));
    }
}
