package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getTasks(){
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks are successfully retrieved.",taskService.listAllTasks(), HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("id")Long id){
        return ResponseEntity
                .ok(new ResponseWrapper("Task is successfully retrieved.",taskService.findById(id), HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO dto){
        taskService.save(dto);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is successfully created.", HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO dto){
        taskService.update(dto);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is successfully updated.", HttpStatus.OK));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId") Long id){
        taskService.delete(id);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is successfully deleted.", HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> taskDTOList= taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks are successfully retrieved.",taskDTOList, HttpStatus.OK));
    }

    @PutMapping("employee/update")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO dto){
        taskService.update(dto);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is successfully updated.", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
        List<TaskDTO> taskDTOList= taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity
                .ok(new ResponseWrapper("Tasks are successfully retrieved.",taskDTOList, HttpStatus.OK));

    }
}
