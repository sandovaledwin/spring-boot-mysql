package com.creaskills.accessingdatamysql.Services;

import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Models.UserDetailsImpl;
import com.creaskills.accessingdatamysql.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return new UserDetailsImpl(user);
    }
}
