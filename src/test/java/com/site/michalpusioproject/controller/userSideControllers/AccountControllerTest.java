package com.site.michalpusioproject.controller.userSideControllers;

import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;


    private static RequestPostProcessor userAcc(){
        return user("user").password("pass").roles("USER").authorities(Collections.singleton(new SimpleGrantedAuthority("USER")));
    }

    @Before
    public void setUp() {
        when(userService.getUserByEmail(anyString())).thenReturn(new User());
    }

    @Test
    public void getProfilePage() throws Exception {
        mvc
                .perform(get("/user/account/profile").with(userAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/account/profile"));
    }

    @Test
    public void getSecurityPage() throws Exception {
        mvc
                .perform(get("/user/account/security").with(userAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/account/security"));
    }

    @Test
    public void updateProfile() throws Exception {
        doNothing().when(userService).editProfile(any(User.class));

        mvc
                .perform(post("/user/account/profile").with(userAcc()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/account/profile"));
    }

    @Test
    public void updatePassword() throws Exception {
        doNothing().when(userService).editPassword(any(User.class));

        mvc
                .perform(post("/user/account/security").with(userAcc()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/account/security"));
    }
}