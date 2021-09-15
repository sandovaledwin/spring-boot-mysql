package com.creaskills.accessingdatamysql.Repositories;

import com.creaskills.accessingdatamysql.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}