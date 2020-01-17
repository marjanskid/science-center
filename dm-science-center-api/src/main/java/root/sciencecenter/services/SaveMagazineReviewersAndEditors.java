package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.repositories.MagazineRepository;
import root.sciencecenter.repositories.UserRepository;

import java.util.List;

@Service
public class SaveMagazineReviewersAndEditors implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> newMagazineDetailsForm =
                (List<FormSubmissionDto>) delegateExecution.getVariable("reviewersAndEditors");

        String reviewer1 = newMagazineDetailsForm.get(0).getFieldValue();
        String reviewer2 = newMagazineDetailsForm.get(1).getFieldValue();
        String editor1 = newMagazineDetailsForm.get(2).getFieldValue();
        String editor2 = newMagazineDetailsForm.get(3).getFieldValue();
        System.out.println("reviewer1: " + reviewer1);
        System.out.println("reviewer2: " + reviewer2);
        System.out.println("editor1: " + editor1);
        System.out.println("editor2: " + editor2);

        List<FormSubmissionDto> newMagazineForm =
                (List<FormSubmissionDto>) delegateExecution.getVariable("newMagazineForm");
        String issnNumberString = newMagazineForm.get(1).getFieldValue();

        Long issnNumber = Long.parseLong(issnNumberString);

        Magazine foundMagazine = magazineRepository.findByIssnNumber(issnNumber);

        System.out.println("Pronasao je casopis sa zadatim brojem i izvrsice update podataka");
    }
}
