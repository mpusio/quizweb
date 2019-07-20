package com.site.michalpusioproject.controller.userSideControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserHomepageController {

    @RequestMapping({"/", ""})
    public String getHomePage(Model model, Authentication authentication){
        String role = authentication.getAuthorities().toArray()[0].toString();

        if(role.equals("ADMIN")){
            model.addAttribute("admin", role);
        }

        return "user/homepage";
    }
}
