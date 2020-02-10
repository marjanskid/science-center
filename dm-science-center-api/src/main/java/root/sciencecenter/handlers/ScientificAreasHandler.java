package root.sciencecenter.handlers;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.entities.ScientificArea;
import root.sciencecenter.repositories.ScientificAreaRepository;

import java.util.HashMap;
import java.util.List;

@Service
public class ScientificAreasHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    @Autowired
    ScientificAreaRepository scientificAreaRepository;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields=delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<ScientificArea> scientificAreas = scientificAreaRepository.findAll();
        for(FormField f : taskFormFields.getFormFields()){
            if (f.getId().equals("info_rad_noblast_id")) {
                HashMap<String, String> mapa = (HashMap<String, String>)f.getType().getInformation("values");
                mapa.clear();
                for (ScientificArea scientificArea : scientificAreas) {
                    mapa.put(scientificArea.getName(), scientificArea.getName());
                }
            }
        }
    }
}
