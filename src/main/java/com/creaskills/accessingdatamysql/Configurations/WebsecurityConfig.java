package com.creaskills.accessingdatamysql.Configurations;

import com.creaskills.accessingdatamysql.Filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebsecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**"
    };

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/auth/welcome").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin/users").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin/user/{^[\\d]$}").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/admin/user").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin/all-courses").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin/courses").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.GET, "/admin/course/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST,"/admin/course").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.PUT,"/admin/course").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.DELETE,"/admin/course/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.GET,"/admin/units/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.GET,"/admin/unit/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST,"/admin/unit/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.PUT,"/admin/unit/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.DELETE,"/admin/unit/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.GET,"/admin/tasks/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.GET,"/admin/task/{^[\\d]$}/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST,"/admin/task/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.PUT,"/admin/task/{^[\\d]$}/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.DELETE,"/admin/task/{^[\\d]$}/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST, "/auth/doLogin")
                .permitAll()
                .antMatchers(SWAGGER_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
