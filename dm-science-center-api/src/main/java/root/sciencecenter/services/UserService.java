package root.sciencecenter.services;

import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.User;

import java.util.List;

public interface UserService {

    boolean checkUserExists(List<FormSubmissionDto> dto);
    boolean activateUserAccount(String username, String hashCode);
    User userSignIn(String username, String password);
}
