package usecase.manageapplications.deleteapplication;

/**
 * Controller for handling the delete application use case.
 */
public class DeleteApplicationController {
    private final DeleteApplicationInputBoundary deleteApplicationInteractor;

    /**
     * Constructs a new DeleteApplicationController instance.
     *
     * @param deleteApplicationInteractor the interactor to handle the delete application logic
     */
    public DeleteApplicationController(DeleteApplicationInputBoundary deleteApplicationInteractor) {
        this.deleteApplicationInteractor = deleteApplicationInteractor;
    }

    /**
     * Deletes an application based on the provided user ID and project ID.
     *
     * @param senderUserId the ID of the user sending the delete request
     * @param projectId the ID of the project associated with the application to be deleted
     */
    public void deleteApplication(int senderUserId, int projectId) {
        DeleteApplicationInputData inputData = new DeleteApplicationInputData(senderUserId, projectId);
        deleteApplicationInteractor.deleteApplication(inputData);
    }
}
