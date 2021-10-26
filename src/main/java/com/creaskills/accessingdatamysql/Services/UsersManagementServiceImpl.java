package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.DTO.UserDTO;
import com.creaskills.accessingdatamysql.Exceptions.UserFoundException;
import com.creaskills.accessingdatamysql.Exceptions.UserNotFoundException;
import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsersManagementServiceImpl implements UsersManagementService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAllBy();
    }

    public UserDTO getUser(Integer userId) {
        return userRepository.findByUserId(userId, UserDTO.class)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserDTO addNewUser(User newUser) {
        String userName = newUser.getUserName();
        User userFound = userRepository.findByUserName(userName);
        if (!Objects.isNull(userFound)) {
            throw new UserFoundException(userName);
        }
        User userCreated = userRepository.save(newUser);
        return new UserDTO(
                userCreated.getUserId(),
                userCreated.getUserName(),
                userCreated.getName(),
                userCreated.getLastName(),
                userCreated.getRole(),
                userCreated.getStatus()
        );
    }
}
