package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.RoleRepository;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class UserController {

    private UserService userService;
    private QuizService quizService;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, QuizService quizService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.quizService = quizService;
    }

    @ModelAttribute
    public void addQuizzesAttribute(Model model){
        model.addAttribute("quizzes", quizService.getAllOrderedQuizzes());
        model.addAttribute("users", userService.getAllUsers());
    }

    @GetMapping("/users")
    public String fetchAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users/users";
    }

    @GetMapping("/users/{id}/view")
    public String getUserById(Model model, @PathVariable Long id){
        model.addAttribute("user", userService.getUserById(id).get());
        return "admin/users/user_view";
    }

    @GetMapping("/users/form")
    public String getFormPage(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", new User());
        return "admin/users/user_form";
    }

    @PostMapping("/users/form")
    public String addUser(@Valid User user){
        userService.addUser(user, user.getRole().getRole());
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editFormUser(Model model, @PathVariable Long id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users/user_edit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUser(@Valid User user){
        userService.editUser(user);
        return "redirect:/admin/users";
    }
}
