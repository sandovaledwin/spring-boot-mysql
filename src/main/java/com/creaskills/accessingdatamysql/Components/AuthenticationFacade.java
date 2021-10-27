package com.creaskills.accessingdatamysql.Components;

import com.creaskills.accessingdatamysql.Models.User;
import com.creaskills.accessingdatamysql.Models.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserDetailsImpl getPrincipal() {
        return (UserDetailsImpl) getAuthentication().getPrincipal();
    }

    public User getUserDetails() {
        return getPrincipal().getUserDetails();
    }

    public String getUserName() {
        return getPrincipal().getUsername();
    }
}
