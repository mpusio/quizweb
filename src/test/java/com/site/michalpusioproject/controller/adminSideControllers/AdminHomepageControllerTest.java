package com.site.michalpusioproject.controller.adminSideControllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminHomepageControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private MockMvc mvc;

    @Before()
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void shouldReturnAdminPage() throws Exception{
        mvc
                .perform(get("/admin").with(user("admin").password("pass").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Here is your admin panel, have fun!")))
                .andExpect(view().name("admin/homepage"));
    }

    @Test
    public void shouldNotReturnAdminPage() throws Exception{
        mvc
                .perform(get("/admin").with(user("user").password("pass").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}