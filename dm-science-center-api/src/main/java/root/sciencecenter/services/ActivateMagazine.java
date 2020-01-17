package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.repositories.MagazineRepository;

import java.util.List;

@Service
public class ActivateMagazine implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> approveMagazineForm =
                (List<FormSubmissionDto>) delegateExecution.getVariable("approveMagazineForm");

        String magazineApproved = approveMagazineForm.get(0).getFieldValue();
        System.out.println("magazineApproved: " + magazineApproved);

        if (magazineApproved.equals("true")) {
            List<FormSubmissionDto> newMagazineForm =
                    (List<FormSubmissionDto>) delegateExecution.getVariable("newMagazineForm");
            String issnNumberString = newMagazineForm.get(1).getFieldValue();
            Long issnNumber = Long.parseLong(issnNumberString);

            Magazine foundMagazine = magazineRepository.findByIssnNumber(issnNumber);
            foundMagazine.setActive(true);

            magazineRepository.save(foundMagazine);
            System.out.println("Casopis sa zadatim issn brojem je aktiviran");
        } else {
            System.out.println("Casopis nije aktiviran");
        }





    }
}
