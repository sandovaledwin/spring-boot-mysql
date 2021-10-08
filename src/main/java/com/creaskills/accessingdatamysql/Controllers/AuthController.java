package com.creaskills.accessingdatamysql.Controllers;

import com.creaskills.accessingdatamysql.Models.AuthRequest;
import com.creaskills.accessingdatamysql.Models.TokenResponse;
import com.creaskills.accessingdatamysql.utils.Jwt;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path="/auth")
public class AuthController {

    @Autowired
    private Jwt jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to javatechie !!";
    }

    @PostMapping("/doLogin")
    public TokenResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return new TokenResponse(authRequest.getUserName(), jwtUtil.generateToken(authRequest.getUserName()));
    }
}
