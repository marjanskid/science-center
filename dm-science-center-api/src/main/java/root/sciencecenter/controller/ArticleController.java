package root.sciencecenter.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.sciencecenter.dtos.FormFieldsDto;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.entities.Magazine;
import root.sciencecenter.services.MagazineService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

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

    @GetMapping(path = "/getNewArticleForm/{username}", produces = "application/json")
    public @ResponseBody FormFieldsDto getNewArticleForm(@PathVariable String username) {
        System.out.println("get new article form");

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("novi_rad");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + " -> " + fp.getType());
        }

        // String token = tokenProvider.getToken(request);
        // String username = tokenProvider.getUsernameFromToken(token);
        runtimeService.setVariable(pi.getId(), "processUser", username);

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/postNewArticleForm/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postNewArticleForm(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("post new article form");
        HashMap<String, Object> map = this.mapListToDto(dto);
        Magazine foundMagazine = magazineService.getMagazineByName(dto.get(0));
        if (foundMagazine != null) {
            System.out.println("magazineExists = true");
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();
            runtimeService.setVariable(processInstanceId, "newArticleMagazineForm", dto);
            formService.submitTaskForm(taskId, map);
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            System.out.println("magazineExists = false");
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/postArticleAuthorBasicInfo/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postArticleAuthorBasicInfo(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("postArticleAuthorBasicInfo form");
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "postArticleAuthorBasicInfo", dto);
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping(path = "/postArticleCoauthorInfo/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postArticleCoauthorInfo(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
        System.out.println("postArticleCoauthorInfo form");
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "postArticleCoauthorInfo", dto);
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
