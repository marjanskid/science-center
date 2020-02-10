package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.enums.MagazineSubscriber;
import root.sciencecenter.repositories.MagazineRepository;

import java.util.List;

@Service
public class MagazinePayment implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> newArticleMagazineForm =
                (List<FormSubmissionDto>) delegateExecution.getVariable("newArticleMagazineForm");

        String selectedMagazine = newArticleMagazineForm.get(0).getFieldValue();
        System.out.println("selectedMagazine: " + selectedMagazine);

        String magazineSubscriber = "reader";
        boolean subscriptionPayed = false;

        Magazine foundMagazine = magazineRepository.findByName(selectedMagazine);
        if (foundMagazine.getMagazineSubscriber().equals(MagazineSubscriber.AUTHOR)) {
            magazineSubscriber = "author";
            if (foundMagazine.isSubscriptionPayed()) {
                subscriptionPayed = true;
            }
        }

        delegateExecution.setVariable("magazineSubscriber", magazineSubscriber);
        delegateExecution.setVariable("subscriptionPayed", subscriptionPayed);

        System.out.println("neki tekst ---------------------------->");
    }
}
