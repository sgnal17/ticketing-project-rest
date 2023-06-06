package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed({"Admin","Manager"})
    public ResponseEntity<ResponseWrapper> getUsers(){
        List<UserDTO> userDTOList=userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users retrieved.",userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("username") String username){
           UserDTO user= userService.findByUserName(username);
           return ResponseEntity.ok(new ResponseWrapper("User retrieved.",user,HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO dto){
        userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User is successfully created.",HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO dto){
        userService.update(dto);
        return ResponseEntity
                .ok(new ResponseWrapper("User is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/username")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username){
        userService.delete(username);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully deleted",HttpStatus.OK));    }

}
