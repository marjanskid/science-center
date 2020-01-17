package root.sciencecenter.services;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.RegistrationHashCode;
import root.sciencecenter.entities.User;
import root.sciencecenter.repositories.RegistrationHashCodeRepository;
import root.sciencecenter.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RegistrationHashCodeRepository registrationHashCodeRepository;

    @Override
    public boolean checkUserExists(List<FormSubmissionDto> dto) {
        System.out.println("checkUserExists!");
        List<User> allUsers = userRepository.findAll();
        if (!allUsers.isEmpty()) {
            System.out.println("notEmpty");
            for (User user: allUsers) {
                System.out.println("for");
                System.out.println(dto.get(7).getFieldValue());
                System.out.println(user.getUsername());
                if (user.getUsername().equals(dto.get(7).getFieldValue())) {
                    System.out.println("Korisnik sa datim username-om: '" + user.getUsername() + "' vec postoji!");
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean activateUserAccount(String username, String hashCode) {
        RegistrationHashCode registrationHashCode = registrationHashCodeRepository.findByUsername(username);
        if (registrationHashCode != null && registrationHashCode.getHashCode().equals(hashCode)) {
            User userToActivate = userRepository.findByUsername(username);
            if (userToActivate != null) {
                userToActivate.setActivatedUser(true);
                userRepository.save(userToActivate);
                return true;
            }
        }

        return false;
    }

    @Override
    public User userSignIn(String username, String password) {
        User checkUser = userRepository.findByUsername(username);
        if (checkUser != null) {
            String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            if (hashedPassword.equals(checkUser.getPassword())) {
                System.out.println("User with given username and password found!");
                return checkUser;
            }
        }

        return null;
    }
}
