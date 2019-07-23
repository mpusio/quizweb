package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.Role;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.RoleRepository;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleRepository roleRepository;

    private static RequestPostProcessor adminAcc() {
        return user("admin").password("pass").roles("ADMIN");
    }

    @Test
    public void fetchAllUsers() throws Exception {
        mvc
                .perform(get("/admin/users").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("admin/users/users"));
    }

    @Test
    public void getUserById() throws Exception {
        Long exampleId = 1L;
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(new User()));

        mvc
                .perform(get("/admin/users/" + exampleId + "/view").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("admin/users/user_view"));
    }

    @Test
    public void getFormPage() throws Exception {
        when(roleRepository.findAll()).thenReturn(new ArrayList());

        mvc
                .perform(get("/admin/users/form").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user", "roles"))
                .andExpect(view().name("admin/users/user_form"));
    }

    @Test
    public void addUser() throws Exception {
        doNothing().when(userService).addUser(any(User.class), anyString());

        User user = new User();
        user.setRole(new Role("ADMIN"));

        mvc
                .perform(post("/admin/users/form").with(adminAcc())
                .flashAttr("user", user))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"));
    }

    @Test
    public void deleteUserById() throws Exception {
        Long exampleId = 1L;
        doNothing().when(userService).deleteUserById(anyLong());

        mvc
                .perform(get("/admin/users/" + exampleId + "/delete").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"));
    }

    @Test
    public void editFormUser() throws Exception {
        Long exampleId = 1L;
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(new User()));
        when(roleRepository.findAll()).thenReturn(new ArrayList<Role>());

        mvc
                .perform(get("/admin/users/" + exampleId + "/edit").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user", "roles"))
                .andExpect(view().name("admin/users/user_edit"));
    }

    @Test
    public void editUser() throws Exception {
        Long exampleId = 1L;
        doNothing().when(userService).editUser(any(User.class));

        mvc
                .perform(post("/admin/users/" + exampleId + "/edit").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/users"));
    }
}