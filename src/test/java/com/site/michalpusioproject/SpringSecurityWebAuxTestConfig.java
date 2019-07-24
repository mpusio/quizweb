package com.site.michalpusioproject;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        SimpleGrantedAuthority userAuthotity = new SimpleGrantedAuthority("USER");
        User basicUser = new User("user@company.com", "password", Collections.singleton(userAuthotity));

        SimpleGrantedAuthority adminAuthotity = new SimpleGrantedAuthority("ADMIN");
        User adminUser = new User("admin@company.com", "password", Collections.singleton(adminAuthotity));


        return new InMemoryUserDetailsManager(Arrays.asList(
                basicUser, adminUser
        ));
    }
}