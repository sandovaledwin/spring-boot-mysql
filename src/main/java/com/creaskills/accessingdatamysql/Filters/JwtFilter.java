package com.creaskills.accessingdatamysql.Filters;

import com.creaskills.accessingdatamysql.DTO.ErrorResponseDTO;
import com.creaskills.accessingdatamysql.Services.UserDetailsServiceImpl;
import com.creaskills.accessingdatamysql.Utils.Jwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private Jwt jwtUtil;

    @Autowired
    private UserDetailsServiceImpl service;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");

            String token = null;
            String userName = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = service.loadUserByUsername(userName);

                if (jwtUtil.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                    LocalDateTime.now().toString(),
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized",
                    ex.getMessage(),
                    request.getRequestURL().toString()
            );
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.getWriter().write(mapper.writeValueAsString(errorResponseDTO));
        }
    }
}