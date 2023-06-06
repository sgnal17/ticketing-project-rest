package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjects(){
        return ResponseEntity.ok(new ResponseWrapper("Projects retrieved.",projectService.listAllProjects(), HttpStatus.OK));
    }

    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByProjectCode(@PathVariable("projectCode") String code){
       ProjectDTO dto= projectService.getByProjectCode(code);
        return ResponseEntity
                .ok(new ResponseWrapper("Project retrieved.",dto,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager","Admin"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO dto){
        projectService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Project is successfully created.",HttpStatus.CREATED));
    }
    @PutMapping()
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO dto){
        projectService.update(dto);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("code")String code){
        projectService.delete(code);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseWrapper("Project is successfully deleted.",HttpStatus.ACCEPTED));
    }

    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        List<ProjectDTO> projectDTOList= projectService.listAllProjectDetails();
        return ResponseEntity
                .ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOList,HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode")String code){
        projectService.complete(code);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is successfully completed",HttpStatus.OK));
    }
}
