package com.site.michalpusioproject.controller.guestsSideControllers;

import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class StarterController {

    private UserService userService;

    @Autowired
    public StarterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "", "/home"})
    public String getStarterPage(SecurityContextHolderAwareRequestWrapper sec) {
        String user = sec.getRemoteUser();

        if (user != null){
            return "redirect:/user";
        }
        return "guest/homepage";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "guest/login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model)
    {
        model.addAttribute("user", new User());
        return "guest/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid User user, BindingResult result, Model model, WebRequest request, Errors errors){
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "guest/registration";
        }
        userService.registerUser(user);
        return "redirect:/home";
    }
}
