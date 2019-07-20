package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.dto.UserAndRoleDto;
import com.site.michalpusioproject.repository.RoleRepository;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/users")
    public String fetchAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users/users";
    }

    @RequestMapping("/users/{id}/view")
    public String getUserById(Model model, @PathVariable Long id){
        model.addAttribute("user", userService.getUserById(id).get());
        return "admin/users/user_view";
    }

    @RequestMapping("/users/form")
    public String getFormPage(Model model) {
        User user = new User();
        model.addAttribute("uar", new UserAndRoleDto());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users/user_form";
    }

    @PostMapping("/users/form")
    public String addUser(@Valid UserAndRoleDto userAndRoleDto, BindingResult bindingResult){
        User user = userAndRoleDto.getUser();
        String nameOfRole = userAndRoleDto.getNameOfRole();

        userService.addUser(user, nameOfRole);
        return "redirect:/admin/users";
    }

    @RequestMapping("/users/{id}/delete")
    public String deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @RequestMapping("/users/{id}/edit")
    public String editFormUser(Model model, @PathVariable Long id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/users/user_edit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUser(@Valid User user, BindingResult bindingResult){
        userService.editUser(user);
        return "redirect:/admin/users";
    }
}
