package com.creaskills.accessingdatamysql.Repositories;

import com.creaskills.accessingdatamysql.DTO.UserDTO;
import com.creaskills.accessingdatamysql.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserName(String userName);
    List<UserDTO> findAllBy();
    <T> Optional<T> findByUserId(Integer userId, Class<T> type);
}