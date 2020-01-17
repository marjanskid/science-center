package root.sciencecenter.services;

import com.google.common.hash.Hashing;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.User;
import root.sciencecenter.enums.UserRole;
import root.sciencecenter.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SaveRegistration implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) delegateExecution.getVariable("Process_17a6cmn");

        String firstName = registration.get(0).getFieldValue();
        String lastName = registration.get(1).getFieldValue();
        String country = registration.get(2).getFieldValue();
        String city = registration.get(3).getFieldValue();
        String title = registration.get(4).getFieldValue();
        String email = registration.get(5).getFieldValue();
        String scientificAreas = registration.get(6).getFieldValue();
        String username = registration.get(7).getFieldValue();
        String password = registration.get(8).getFieldValue();
        String reviewer = registration.get(9).getFieldValue();
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);
        System.out.println("country: " + country);
        System.out.println("city: " + city);
        System.out.println("title: " + title);
        System.out.println("email: " + email);
        System.out.println("scientificAreas: " + scientificAreas);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("reviewer: " + reviewer);

        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        User newUser = new User(firstName, lastName, city, country, title, email, scientificAreas, username,
                                hashedPassword, false);
        newUser.setUserRole(UserRole.USER);

        try {
            userRepository.save(newUser);
            System.out.println("Cuvanje uspesno");
        } catch(NullPointerException e) {
            System.out.println("Cuvanje nije uspesno");
        }
    }
}
