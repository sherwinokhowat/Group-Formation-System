package usecase.acceptapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Factory class for creating instances of the AcceptApplication use case.
 */
public class AcceptApplicationUseCaseFactory {

    private final static IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
    private final static IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private final static IUserRepository userRepository = DataAccessConfig.getUserRepository();

    // Private constructor to prevent instantiation
    private AcceptApplicationUseCaseFactory() {}

    /**
     * Creates and returns an AcceptApplicationController instance.
     *
     * @param displayProjectApplicationViewModel the view model to update with acceptance results.
     * @return an AcceptApplicationController instance.
     */
    public static AcceptApplicationController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        AcceptApplicationOutputBoundary presenter = new AcceptApplicationPresenter(displayProjectApplicationViewModel);
        AcceptApplicationInputBoundary interactor = new AcceptApplicationInteractor(presenter, applicationRepository,
                                                                                    userProjectsRepository, userRepository);
        return new AcceptApplicationController(interactor);
    }
}
