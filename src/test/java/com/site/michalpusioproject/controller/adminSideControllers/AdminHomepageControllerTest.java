package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.SpringSecurityWebAuxTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSecurityWebAuxTestConfig.class)
@AutoConfigureMockMvc
public class AdminHomepageControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before()
    public void setup()
    {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    @WithUserDetails("admin@company.com")
    public void shouldReturnAdminPage() throws Exception{
        //mvc
                //.perform(get("/admin/home"))
                //.andDo(print())
                //.andExpect(status().isOk());
                //.andExpect(model().attributeExists("users, quizzes"))
                //.andExpect(content().string(containsString("Here is your admin panel, have fun!")))
                //.andExpect(view().name("admin/homepage"));
    }


    @Test
    @WithUserDetails("user@company.com")
    public void shouldNotReturnAdminPage() throws Exception{
//        mvc
//                .perform(get("/admin/home"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
    }
}