package com.site.michalpusioproject.controller.userSideControllers;

import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.security.DynamicSecurity;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/account")
public class AccountController {

    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addAttributeToModel(Model model, SecurityContextHolderAwareRequestWrapper sec){
        model.addAttribute("user", userService.getUserByEmail(sec.getRemoteUser()));
    }

    @GetMapping({"/profile", "/", ""})
    public String getProfilePage(){
        return "user/account/profile";
    }

    @GetMapping("/security")
    public String getSecurityPage(){
        return "user/account/security";
    }

    @PostMapping({"/profile", "/", ""})
    public String updateProfile(@Valid User user){
        userService.editProfile(user);
        DynamicSecurity.refreshAuthenticationAfterLoggingIn(user);
        return "redirect:/user/account/profile";
    }

    @PostMapping("/security")
    public String updatePassword(@Valid User user){
        userService.editPassword(user);
        return "redirect:/user/account/security";
    }

}