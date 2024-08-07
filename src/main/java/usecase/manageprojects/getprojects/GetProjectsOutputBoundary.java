package usecase.manageprojects.getprojects;

/**
 * Output boundary interface for retrieving projects.
 * Defines methods to prepare success and failure views.
 */
public interface GetProjectsOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(GetProjectsOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
