package com.site.michalpusioproject.controller.guestsSideControllers;

import com.site.michalpusioproject.domains.Role;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Valid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StarterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void getStarterPage() throws Exception{
        mvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("guest/homepage"));
    }

    @Test
    @WithMockUser(username = "username", roles={"USER"})
    public void getStarterPageAsUser() throws Exception{
        mvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/homepage"));
    }

    @Test
    public void getLoginPage() throws Exception {
        mvc
                .perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("guest/login"));
    }

    @Test
    public void getRegistrationPage() throws Exception{
        mvc
                .perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("guest/registration"))
                .andExpect(model().attributeExists("user"));
    }

//    @Test
//    public void registerUserWithCorrectFields() throws Exception{
//        User user = User.builder()
//                .firstName("Michal")
//                .lastName("Pusio")
//                .password("password123")
//                .email("pusio@gmail.com")
//                .build();
//
//
//
//        mvc
//                .perform(post("/registration", user))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("guest/registration"));
//    }

    @Test
    public void registerUserWithInCorrectFields() throws Exception {
        @Valid
        User user = new User(1L, "wrong", "illegal", "parameters", "likeThis", new Role("USER"));

//        BindingResult result = mock(BindingResult.class);
//        when(result.hasErrors()).thenReturn(false);

        mvc
                .perform(post("/registration", user))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("guest/registration"));

    }
}