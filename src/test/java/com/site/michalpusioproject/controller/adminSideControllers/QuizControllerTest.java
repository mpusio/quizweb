package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.Answer;
import com.site.michalpusioproject.domains.Question;
import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.service.QuizService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QuizControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private HttpServletRequest request;

    @MockBean
    private QuizService quizService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    private static RequestPostProcessor adminAcc() {
        return user("admin").password("pass").roles("ADMIN").authorities(Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
    }

    private static RequestPostProcessor userAcc(){
        return user("user").password("pass").roles("USER").authorities(Collections.singleton(new SimpleGrantedAuthority("USER")));
    }

    @Test
    public void getOrderedQuizzes() throws Exception{
        mvc
                .perform(get("/admin/quizzes").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quizzes"));
    }

    @Test
    public void getQuizById() throws Exception{
        when(quizService.getQuizById(anyLong())).thenReturn(Optional.of(new Quiz()));
        Long exampleId = 5L;

        mvc
                .perform(get("/admin/quizzes/" + exampleId +"/view").with(adminAcc()))
                .andDo(print())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quiz_view"));
    }

    @Test
    public void editQuizById() throws Exception {
        when(quizService.getQuizById(anyLong())).thenReturn(Optional.of(new Quiz()));
        Long exampleId = 4L;

        mvc
                .perform(get("/admin/quizzes/" + exampleId +"/edit").with(adminAcc()))
                .andDo(print())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quiz_form"));
    }

    @Test
    public void deleteQuizById() throws Exception {
        doNothing().when(quizService).deleteQuizById(anyLong());
        Long exampleId = 6L;

        mvc
                .perform(get("/admin/quizzes/" + exampleId +"/delete").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/quizzes"));
    }

    @Test
    public void getFormPage() throws Exception {
        mvc
                .perform(get("/admin/quizzes/form").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(view().name("admin/quizzes/quiz_form"));
    }

    @Test
    public void addQuiz() throws Exception {
        doNothing().when(quizService).insertOrUpdateQuiz(any(Quiz.class));

        mvc
                .perform(post("/admin/quizzes/form").with(adminAcc()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/quizzes"));
    }

    @Test
    public void addQuestion() throws Exception {
        Quiz quiz = new Quiz();
        Question question = new Question();
        Answer answer = new Answer();

        question.setAnswers(new ArrayList<>());
        question.getAnswers().add(answer);

        quiz.setQuestions(new ArrayList<>());
        quiz.getQuestions().add(question);

        mvc
                .perform(post("/admin/quizzes/form?addQuestion=?", "addQuestion")
                        .flashAttr("quiz", quiz).with(adminAcc()))

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quiz_form"));
    }

//    @Test
//    public void removeQuestion() throws Exception {
//        Quiz quiz = new Quiz();
//
//        Question question = new Question();
//        Answer answer = new Answer();
//
//        question.setAnswers(new ArrayList<>());
//        question.getAnswers().add(answer);
//
//        Question question1 = new Question();
//        Answer answer1 = new Answer();
//
//        question.setAnswers(new ArrayList<>());
//        question.getAnswers().add(answer1);
//
//        quiz.setQuestions(new ArrayList<>());
//        quiz.getQuestions().add(question);
//        quiz.getQuestions().add(question1);
//
//
//        when(request.getParameter(anyString())).thenReturn("1");
//
//        mvc
//                .perform(post("/admin/quizzes/form?removeQuestion=?", "removeQuestion")
//                        .flashAttr("quiz", quiz)
//                        .flashAttr("req", request)
//                        .with(adminAcc()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin/quizzes/quiz_form"));
//    }
//
//    @Test
//    public void addAnswer() {
//
//
//    }
//
//    @Test
//    public void removeAnswer() {
//
//
//    }
}