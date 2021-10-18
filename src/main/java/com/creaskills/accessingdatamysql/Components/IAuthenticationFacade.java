package com.creaskills.accessingdatamysql.Components;

import com.creaskills.accessingdatamysql.Models.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    UserDetailsImpl getPrincipal();
}
