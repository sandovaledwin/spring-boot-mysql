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
                .antMatchers(HttpMethod.GET,"/courses-management/list-all").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/courses-management/list-by-user").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST,"/courses-management/add").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.GET,"/courses-management/get-units/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST,"/courses-management/save-unit/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.PUT,"/courses-management/update-unit/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.DELETE,"/courses-management/delete-unit/{^[\\d]$}/{^[\\d]$}").hasAnyRole("ADMIN", "PROFESSOR")
                .antMatchers(HttpMethod.POST, "/auth/doLogin")
                .permitAll().anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
