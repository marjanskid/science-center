package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.User;
import root.sciencecenter.repositories.UserRepository;

import java.util.List;

@Service
public class ChooseArticleChiefEditor implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("------------------------------------------");
        System.out.println("ChooseArticleChiefEditor");

        List<FormSubmissionDto> postArticleAuthorBasicInfo =
                (List<FormSubmissionDto>) delegateExecution.getVariable("postArticleAuthorBasicInfo");

        String scientificArea = postArticleAuthorBasicInfo.get(3).getFieldValue();
        System.out.println("scientificArea: " + scientificArea);

        List<User> allUsers = userRepository.findAll();
        String articleChiefEditor = "";
        for (User user : allUsers) {
            if (user.getScientificArea().getName().equals(scientificArea)) {
                articleChiefEditor = user.getUsername();
                break;
            }
        }

        if (articleChiefEditor.isEmpty()) {
            articleChiefEditor = (String) delegateExecution.getVariable("chiefEditor");
        }

        System.out.println("articleChiefEditor: " + articleChiefEditor);
        delegateExecution.setVariable("articleChiefEditor", articleChiefEditor);
    }
}
