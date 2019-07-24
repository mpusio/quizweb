package com.site.michalpusioproject.controller.userSideControllers;

import org.assertj.core.util.Arrays;
import org.hibernate.mapping.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserHomepageControllerTest {

    private static RequestPostProcessor userAcc(){
        return user("user").password("pass").roles("USER").authorities(Collections.singleton(new SimpleGrantedAuthority("USER")));
    }

    @Autowired
    private MockMvc mvc;

    @Mock
    private Authentication authentication;

    @Test
    public void getHomePageByUser() throws Exception {
        mvc
                .perform(get("/user").with(userAcc()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/homepage"));
    }
}