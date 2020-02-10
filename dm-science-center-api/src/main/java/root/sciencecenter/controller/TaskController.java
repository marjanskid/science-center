package root.sciencecenter.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.sciencecenter.dtos.FormFieldsDto;
import root.sciencecenter.dtos.FormSubmissionDto;
import root.sciencecenter.dtos.TaskDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    IdentityService identityService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @PostMapping(path = "/postTask/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);
        for (Map.Entry mapElement : map.entrySet()) {
            String key = (String) mapElement.getKey();
            if (key.equals("info_rad_noblast_id") || key.equals("Recenzenti") || key.equals("Urednici")) {
                mapElement.setValue(null);
            }
        }

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "dto", dto);

        try {
            formService.submitTaskForm(taskId, map);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uneti podaci nisu validni!");
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(path = "/getAllMyTasks/{username}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> allMyTasks(@PathVariable String username) {
        System.out.println("allMyTasks");
        // String token = tokenProvider.getToken(request);
        // String username = tokenProvider.getUsernameFromToken(token);

        List<Task> tasks = taskService.createTaskQuery().list();
        List<Task> myTasks = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getAssignee() != null) {
                if (t.getAssignee().equals(username)) {
                    myTasks.add(t);
                }
            }
        }

        List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : myTasks) {
            TaskFormData tfd = formService.getTaskFormData(task.getId());
            List<FormField> properties = tfd.getFormFields();
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee(), properties);
            dtos.add(t);
        }

        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    @GetMapping(path = "/getTasks/{processInstanceId}/{username}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTasks(@PathVariable String processInstanceId, @PathVariable String username) {
        System.out.println("getTasks");
        System.out.println("getMyNextTask - front");
        // String token = tokenProvider.getToken(request);
        // String username = tokenProvider.getUsernameFromToken(token);

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if (task != null) {
            System.out.println(task.getAssignee());
        }

        if (task != null && (task.getAssignee() == null || task.getAssignee().equals(username))) {
            System.out.println("Postoji neki task");
            TaskFormData tfd = formService.getTaskFormData(task.getId());
            List<FormField> properties = tfd.getFormFields();
            TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee(), properties);
            return new FormFieldsDto(task.getId(), task.getProcessInstanceId(), properties);
        }

        return null;
    }

    @GetMapping(path = "/getTask/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<TaskDto> getTask(@PathVariable String taskId) {
        System.out.println("getTask");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskFormData tfd = formService.getTaskFormData(taskId);

        List<FormField> properties = tfd.getFormFields();
        TaskDto t = new TaskDto(taskId, task.getName(), task.getAssignee(), properties);

        return new ResponseEntity(t, HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list) {
        HashMap<String, Object> map = new HashMap<>();
        for (FormSubmissionDto temp : list) {
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
