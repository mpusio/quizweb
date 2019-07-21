package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminHomepageController {

    private UserService userService;
    private QuizService quizService;

    @Autowired
    public AdminHomepageController(UserService userService, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
    }

    @RequestMapping({"/", ""})
    public String getHomepage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("quizzes", quizService.getAllOrderedQuizzes());

        return "admin/homepage";
    }
}
