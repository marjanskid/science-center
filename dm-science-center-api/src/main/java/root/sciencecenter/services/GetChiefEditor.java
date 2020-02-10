package root.sciencecenter.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.entities.User;
import root.sciencecenter.repositories.MagazineRepository;

import java.util.List;

@Service
public class GetChiefEditor implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("chiefEditor set");
        List<FormSubmissionDto> newArticleMagazineForm =
                (List<FormSubmissionDto>) delegateExecution.getVariable("newArticleMagazineForm");
        String magazineName = newArticleMagazineForm.get(0).getFieldValue();
        System.out.println("magazineName - " + magazineName);
        String chiefEditor = "";
        Magazine foundMagazine = magazineRepository.findByName(magazineName);
        if (foundMagazine != null) {
            User chiefEditorUser = foundMagazine.getChiefEditor();
            chiefEditor = chiefEditorUser.getUsername();
        }

        delegateExecution.setVariable("chiefEditor", chiefEditor);
    }
}
