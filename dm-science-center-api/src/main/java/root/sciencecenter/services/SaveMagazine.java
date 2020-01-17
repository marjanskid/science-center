package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.entities.User;
import root.sciencecenter.enums.MagazineSubscriber;
import root.sciencecenter.repositories.MagazineRepository;
import root.sciencecenter.repositories.UserRepository;

import java.util.List;

@Service
public class SaveMagazine implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<FormSubmissionDto> newMagazineForm = (List<FormSubmissionDto>) delegateExecution.getVariable("newMagazineForm");

        String name = newMagazineForm.get(0).getFieldValue();
        String issnNumberString = newMagazineForm.get(1).getFieldValue();
        String scientificAreas = newMagazineForm.get(2).getFieldValue();
        String authorPaysSubscription = newMagazineForm.get(3).getFieldValue();
        System.out.println("name: " + name);
        System.out.println("issnNumber: " + issnNumberString);
        System.out.println("scientificAreas: " + scientificAreas);
        System.out.println("authorPaysSubscription: " + authorPaysSubscription);

        Long issnNumber = Long.parseLong(issnNumberString);
        MagazineSubscriber magazineSubscriber = getWhoPaysSubscription(authorPaysSubscription);
        User chiefReviewer = userRepository.getOne(Long.valueOf(1));

        Magazine newMagazine = new Magazine(name, issnNumber, scientificAreas, magazineSubscriber, chiefReviewer);

        try {
            magazineRepository.save(newMagazine);
            System.out.println("Cuvanje uspesno");
        } catch(NullPointerException e) {
            System.out.println("Cuvanje nije uspesno");
        }
    }

    private MagazineSubscriber getWhoPaysSubscription(String inputString) {
        boolean authorPaysSubscription = false;
        MagazineSubscriber magazineSubscriber = MagazineSubscriber.READER;
        if (inputString.equals("true")) {
            magazineSubscriber = MagazineSubscriber.AUTHOR;
        }

        return magazineSubscriber;
    }
}
