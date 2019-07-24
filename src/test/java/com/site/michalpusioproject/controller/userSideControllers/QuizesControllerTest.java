package com.site.michalpusioproject.controller.userSideControllers;

import com.site.michalpusioproject.domains.*;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.ResultService;
import com.site.michalpusioproject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuizesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private QuizService quizService;

    @MockBean
    private ResultService resultService;


    private static RequestPostProcessor userAcc(){
        return user("user").password("pass").roles("USER").authorities(Collections.singleton(new SimpleGrantedAuthority("USER")));
    }

    @Test
    public void getQuizzesPage() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setQuestions(new ArrayList<>());

        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        when(quizService.getAllOrderedQuizzes()).thenReturn(Arrays.asList(quiz));
        when(resultService.findAllResultsByUser(any(User.class))).thenReturn(Arrays.asList(new Result(10, new Quiz(), new User())));

        mvc
                .perform(get("/user/quizzes").with(userAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quizzes", "results"))
                .andExpect(view().name("user/quizzes"));
    }

    @Test
    public void getQuizByIdToSolve() throws Exception {
        Quiz quiz = new Quiz();
        Question question = new Question("example question");
        question.setAnswers(Collections.singletonList(new Answer("exmaple answer")));

        quiz.setQuestions(Arrays.asList(question));

        when(quizService.getQuizById(anyLong())).thenReturn(Optional.of(quiz));

        mvc
                .perform(get("/user/quizzes/"  + 1 + "/solution").with(userAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(view().name("user/solution"));
    }

    @Test
    public void checkAnswersAndReturnResult() throws Exception {
        when(quizService.getQuizById(anyLong())).thenReturn(Optional.of(new Quiz()));
        when(quizService.matchQuizAnswers(any(Quiz.class), any(Quiz.class))).thenReturn("1/2");
        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        doNothing().when(resultService).addOrUpdateResult(any(Quiz.class), any(User.class), anyInt());


        mvc
                .perform(post("/user/quizzes/"  + 1 + "/solution").with(userAcc())
                        .flashAttr("quiz", new Quiz()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(view().name("user/result"));

    }

    @Test
    public void getResultPage() throws Exception {

        mvc
                .perform(get("/user/quizzes/"  + 1 + "/result").with(userAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/result"));
    }
}