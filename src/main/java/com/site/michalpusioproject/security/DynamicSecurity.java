package com.site.michalpusioproject.security;

import com.site.michalpusioproject.domains.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class DynamicSecurity {
    public static void refreshAuthenticationAfterLoggingIn(User user){
        String password = user.getPassword();
        String username = user.getEmail();

        Collection<SimpleGrantedAuthority> nowAuthorities =(Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password, nowAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
