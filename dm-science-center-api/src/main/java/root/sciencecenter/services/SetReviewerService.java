package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.User;
import root.sciencecenter.enums.UserRole;
import root.sciencecenter.repositories.UserRepository;

import java.util.List;

@Service
public class SetReviewerService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> approveReviewer = (List<FormSubmissionDto>) delegateExecution.getVariable("approveReviewer");
        String username = approveReviewer.get(0).getFieldValue();
        System.out.println("username: " + username);
        String reviewerApproved = approveReviewer.get(1).getFieldValue();
        System.out.println("reviewerApproved: " + reviewerApproved);

        User foundUser = userRepository.findByUsername(username);
        if (foundUser != null && reviewerApproved.equals("true")) {
            foundUser.setUserRole(UserRole.REVIEWER);
            userRepository.save(foundUser);
            System.out.println("User with username: " + username + " has been approved as reviewer!");
        } else {
            System.out.println("There's no such a user with username: " + username);
        }
    }
}
