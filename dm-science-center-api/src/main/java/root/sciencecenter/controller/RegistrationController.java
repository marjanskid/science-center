package root.sciencecenter.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.sciencecenter.dtos.FormFieldsDto;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.ScientificArea;
import root.sciencecenter.services.ScientificAreaService;
import root.sciencecenter.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    ExternalTaskService externalTaskService;

    @Autowired
    UserService userService;

    @Autowired
    ScientificAreaService scientificAreaService;

    @GetMapping(path = "/getRegistrationForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getRegistrationForm() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_17a6cmn");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        List<ScientificArea> allScientificAreas = scientificAreaService.getAllScientificAreas();

        for (FormField ff : properties) {
            System.out.println(ff.getLabel());
            if (ff.getId().equals("registracija_noblasti_id")) {
                EnumFormType enumFormType = (EnumFormType) ff.getType();
                Map<String, String> values = enumFormType.getValues();
                for (ScientificArea sa : allScientificAreas) {
                    values.put(sa.getId().toString(), sa.getName());
                }
                values.put("0", "izaberite oblast...");
            }
        }

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/postForm/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("post registration form");
        HashMap<String, Object> map = this.mapListToDto(dto);
        if (!userService.checkUserExists(dto)) {
            System.out.println("userExists");
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            runtimeService.setVariable(processInstanceId, "Process_17a6cmn", dto);
            formService.submitTaskForm(taskId, map);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("userExists = false");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/activateUserAccount/{username}/{hashCode}", produces = "application/json")
    public @ResponseBody ResponseEntity getHash(@PathVariable String username, @PathVariable String hashCode) {
        System.out.println(hashCode);
        if (userService.activateUserAccount(username, hashCode)) {
            List<LockedExternalTask> tasks = externalTaskService.fetchAndLock(1, "externalWorkerId")
                    .topic("User account activation", 60L * 1000L)
                    .execute();

            for (LockedExternalTask task : tasks) {
                System.out.println("Sta se radi u ovoj for petlji");
                try {
                    String topic = task.getTopicName();
                    externalTaskService.complete(task.getId(), "externalWorkerId");
                }
                catch(Exception e) {
                    System.out.println("External task nije uspesno zavrsen!");
                }
            }

            String content = "<header>" + "<h1>Korisnicki nalog je uspesno aktiviran!</h1>" + "</header>";
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.TEXT_HTML);

            return new ResponseEntity<String>("Aktivacija korisnickog naloga je uspesna", HttpStatus.OK);
        }


        return new ResponseEntity<String>("Desila se greska prilikom aktivacije korisnickog naloga", HttpStatus.OK);
    }

    @GetMapping(path = "/getReviewerForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getReviewerForm() {
        System.out.println("getReviewerForm");
        Task task = taskService.createTaskQuery().taskDefinitionKey("Task_0aa6msb").singleResult();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), "", properties);
    }

    @PostMapping(path = "/postReviewerForm/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postFormConfirmReviewer(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);
        String responseMessage = "User has't been approved as reviewer!";
        String reviewerApproved = dto.get(1).getFieldValue();
        if (reviewerApproved.equals("true")) {
            responseMessage = "User will be approved as reviewer!";
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "approveReviewer", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
