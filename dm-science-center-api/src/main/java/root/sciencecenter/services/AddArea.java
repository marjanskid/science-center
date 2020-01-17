package root.sciencecenter.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class AddArea implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String naucnaOblast = (String) delegateExecution.getVariable("registracija_username_id");
        System.out.println("kurcinaaaa: " + naucnaOblast);
    }
}
