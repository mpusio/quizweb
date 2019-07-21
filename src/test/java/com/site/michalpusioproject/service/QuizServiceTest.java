package com.site.michalpusioproject.service;


import com.site.michalpusioproject.domains.Answer;
import com.site.michalpusioproject.domains.Question;
import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.repository.QuizRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;

    private List<Quiz> quizzes;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        quizzes = new ArrayList<>();

        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("BPL quiz");
        quiz.setDescription("Check your soccer knowledge in our quiz. Good luck!");
        quiz.setQuestions(null);

        when(quizRepository.save(quiz)).thenReturn(quiz);
        when(quizRepository.findAllByOrderByTitleAsc()).thenReturn(quizzes);
        when(quizRepository.findById(anyLong())).thenReturn(Optional.ofNullable(quiz));
        doNothing().when(quizRepository).deleteById(anyLong());

        quizzes.add(quiz);
    }

    @Test
    public void shouldGetAllOrderedQuizzes() {
        Long id = 1L;

        Quiz found = quizService.getQuizById(id).get();

        assertThat(found).isEqualToComparingFieldByField(quiz);
        assertThat(quizService.getAllOrderedQuizzes().size()).isEqualTo(1);
    }

    @Test
    public void shouldGetQuizById() {
        Long id = 1L;

        Quiz found = quizService.getQuizById(id).get();

        assertThat(found).isEqualToComparingFieldByField(quiz);
    }

    @Test
    public void shouldDeleteQuizById() {
        Long id = 1L;

        quizService.deleteQuizById(id);

        assertThat(quizService.getAllOrderedQuizzes().size()).isEqualTo(1);
    }

    @Test
    public void shouldInsertOrUpdateQuiz() {
        //insert
        quizService.insertOrUpdateQuiz(quiz);
        assertThat(quizService.getQuizById(1L).get()).isEqualTo(quiz);
        assertThat(quizService.getAllOrderedQuizzes().size()).isEqualTo(1);

        //update
        quiz.setTitle("Different title");
        quiz.setDescription("Different description");
        quizService.insertOrUpdateQuiz(quiz);
        assertThat(quizService.getQuizById(1L).get()).isEqualTo(quiz);
        assertThat(quizService.getAllOrderedQuizzes().size()).isEqualTo(1);

    }

    @Test
    public void shouldMatchQuizAnswers() {
        Quiz quiz1 = new Quiz();
        Quiz quiz2 = new Quiz();

        Question question1 = new Question();
        Question question2 = new Question();

        Answer answer1 = new Answer("desc");
        Answer answer2 = new Answer("second desc");

        question1.setAnswers(Collections.singletonList(answer1));
        question2.setAnswers(Collections.singletonList(answer2));

        quiz1.setQuestions(Collections.singletonList(question1));
        quiz2.setQuestions(Collections.singletonList(question2));

        String matchingResult = quizService.matchQuizAnswers(quiz1, quiz2);

        int achievedPoints = Integer.valueOf(matchingResult.split("/")[0]);

        assertThat(achievedPoints).isEqualTo(0);
    }
}