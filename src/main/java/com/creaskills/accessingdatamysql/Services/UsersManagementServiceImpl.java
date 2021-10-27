package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.DTO.UserDTO;
import com.creaskills.accessingdatamysql.Exceptions.UserFoundException;
import com.creaskills.accessingdatamysql.Exceptions.UserNotFoundException;
import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsersManagementServiceImpl implements UsersManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUser(Integer userId) {
        return userRepository.findByUserId(userId)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserDTO addNewUser(User newUser) {
        String userName = newUser.getUserName();
        if (userRepository.findByUserName(userName).isPresent()) {
            throw new UserFoundException(userName);
        }
        User userCreated = userRepository.save(newUser);
        return modelMapper.map(userCreated, UserDTO.class);
    }

    public UserDTO updateUser(Integer userId, User userUpdated) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(userUpdated.getName());
        user.setLastName(userUpdated.getLastName());
        user.setRole(userUpdated.getRole());
        user.setStatus(userUpdated.getStatus());
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }
}
