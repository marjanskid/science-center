package root.sciencecenter.handlers;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.repositories.MagazineRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class MagazinesHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<Magazine> magazines = magazineRepository.findAll();
        for(FormField f : taskFormFields.getFormFields()){
            if( f.getId().equals("odabir_casopisa")){
                HashMap<String, String> mapa = (HashMap<String, String>)f.getType().getInformation("values");
                mapa.clear();
                for(Magazine m : magazines) {
                    mapa.put(m.getName(), m.getName());
                }
            }
        }
    }
}
