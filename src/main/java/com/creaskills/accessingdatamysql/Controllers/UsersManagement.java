package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.DTO.UserDTO;
import com.creaskills.accessingdatamysql.Models.Course;
import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Services.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return usersManagement.getUser(userId);
    }

    @PostMapping("/user")
    UserDTO addNewUser(@RequestBody User newUser) {
        return usersManagement.addNewUser(newUser);
    }
}
