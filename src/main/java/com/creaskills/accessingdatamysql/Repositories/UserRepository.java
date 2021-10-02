package com.creaskills.accessingdatamysql.Repositories;

import com.creaskills.accessingdatamysql.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUserName(String userName);
}