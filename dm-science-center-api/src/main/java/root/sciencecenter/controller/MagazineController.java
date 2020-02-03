package root.sciencecenter.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.sciencecenter.dtos.FormFieldsDto;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.services.MagazineService;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/magazine")
public class MagazineController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    MagazineService magazineService;

    @GetMapping(path = "/getNewMagazineForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getNewMagazineForm() {
        System.out.println("get new magazine form");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("newMagazine");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/postNewMagazineForm/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postNewMagazineForm(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("post new magazine form");
        HashMap<String, Object> map = this.mapListToDto(dto);
        if (!magazineService.checkMagazineExists(dto)) {
            System.out.println("magazineExists = false");
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            runtimeService.setVariable(processInstanceId, "newMagazineForm", dto);
            formService.submitTaskForm(taskId, map);
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            System.out.println("magazineExists = true");
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getReviewersAndEditorsForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getReviewersAndEditorsForm() {
        System.out.println("getReviewersAndEditorsForm");
        Task task = taskService.createTaskQuery().taskDefinitionKey("Task_0k8h948").singleResult();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), "", properties);
    }

    @PostMapping(path = "/postReviewersAndEditorsForm/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postReviewersAndEditorsForm(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("postReviewersAndEditorsForm");
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "reviewersAndEditors", dto);
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping(path = "/getApproveMagazineForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getApproveMagazineForm() {
        System.out.println("getApproveMagazineForm");
        Task task = taskService.createTaskQuery().taskDefinitionKey("Task_1l0o6wh").singleResult();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), "", properties);
    }

    @PostMapping(path = "/postApproveMagazineForm/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postApproveMagazineForm(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("postApproveMagazineForm");
        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "approveMagazineForm", dto);
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        System.out.println("magazine - getAll");
        return new ResponseEntity<>(magazineService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/buyItemWithId", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buyItemWithId(@RequestBody Long itemId) {
        System.out.println("magazine - buyItemWithId");
        System.out.println("itemId: " + itemId);

        return new ResponseEntity<>("sve je kul", HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
