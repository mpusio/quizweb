package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.Answer;
import com.site.michalpusioproject.domains.Question;
import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "username", roles={"ADMIN"})
public class QuizControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuizService quizService;

    @Test
    public void getOrderedQuizzes() throws Exception{
        mvc
                .perform(get("/admin/quizzes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quizzes"));
    }

    @Test
    public void getQuizById() throws Exception{
        when(quizService.getQuizById(anyLong())).thenReturn(Optional.of(new Quiz()));
        Long exampleId = 1L;

        mvc
                .perform(get("/admin/quizzes/" + exampleId +"/view"))
                .andDo(print())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quiz_view"));
    }

    @Test
    public void editQuizById() throws Exception {
        when(quizService.getQuizById(anyLong())).thenReturn(Optional.of(new Quiz()));
        Long exampleId = 2L;

        mvc
                .perform(get("/admin/quizzes/" + exampleId +"/edit"))
                .andDo(print())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quiz_form"));
    }

    @Test
    @WithMockUser(username = "username", roles={"ADMIN", "USER"})
    public void deleteQuizById() throws Exception {
        doNothing().when(quizService).deleteQuizById(anyLong());
        Long exampleId = 20L;

        mvc
                .perform(get("/admin/quizzes/" + exampleId +"/delete"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin/quizzes"));
    }

    @Test
    public void getFormPage() throws Exception {
        mvc
                .perform(get("/admin/quizzes/form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(view().name("admin/quizzes/quiz_form"));
    }

    @Test
    public void addQuiz() throws Exception {
        doNothing().when(quizService).insertOrUpdateQuiz(any(Quiz.class));

        mvc
                .perform(post("/admin/quizzes/form"))
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
                        .sessionAttr("quiz", quiz))


                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("admin/quizzes/quiz_form"));
    }
//
//    @Test
//    public void removeQuestion() throws Exception {
//        mvc
//                .perform(post("/admin/quizzes/form?removeQuestion=?", "removeQuestion"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("admin/quizzes/quiz_form"));
//
//
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