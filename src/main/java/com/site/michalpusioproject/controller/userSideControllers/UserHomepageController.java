package com.site.michalpusioproject.controller.userSideControllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping(value = "/user")
public class UserHomepageController {

    @GetMapping({"/", ""})
    public String getHomePage(Model model, Authentication authentication, SecurityContextHolderAwareRequestWrapper sec){
        String role = authentication.getAuthorities().toArray()[0].toString();

        if(role.equals("ADMIN")){
            model.addAttribute("admin", role);
        }

        return "user/homepage";
    }
}
