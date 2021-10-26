package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.DTO.UserDTO;
import com.creaskills.accessingdatamysql.Models.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UsersManagementService {
    List<UserDTO> getAllUsers();
    UserDTO getUser(Integer userId);
    UserDTO addNewUser(User newUser);
}
