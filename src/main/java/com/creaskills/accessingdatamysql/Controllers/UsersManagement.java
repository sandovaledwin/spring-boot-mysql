package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.DTO.UserDTO;
import com.creaskills.accessingdatamysql.Exceptions.UserFoundException;
import com.creaskills.accessingdatamysql.Exceptions.UserNotFoundException;
import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Services.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/admin")
public class UsersManagement {

    @Autowired
    private UsersManagementService usersManagement;

    @GetMapping("/users")
    List<UserDTO> getAllUsers() {
        return usersManagement.getAllUsers();
    }

    @GetMapping("/user/{userId}")
    UserDTO getUser(@PathVariable Integer userId) {
        try {
            return usersManagement.getUser(userId);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The user doesn't exist", ex);
        }
    }

    @PostMapping("/user")
    UserDTO addNewUser(@RequestBody User newUser) {
        try {
            return usersManagement.addNewUser(newUser);
        } catch (UserFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "A user with the same username already exists on the system", ex);
        }
    }

    @PutMapping("user/{userId}")
    UserDTO updateUser(@PathVariable Integer userId, @RequestBody User user) {
        try {
            return usersManagement.updateUser(userId, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The user doesn't exist", ex);
        }
    }
}
