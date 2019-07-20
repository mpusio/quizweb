package com.site.michalpusioproject.repository;

import com.site.michalpusioproject.domains.Quiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuizRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void shouldFindAllByOrderByTitleAsc() {
        //given
        Quiz quiz1 = new Quiz();
        quiz1.setTitle("Z quiz");

        Quiz quiz2 = new Quiz();
        quiz2.setTitle("A quiz");

        Quiz quiz3 = new Quiz();
        quiz3.setTitle("T quiz");

        entityManager.persist(quiz1);
        entityManager.persist(quiz2);
        entityManager.persist(quiz3);

        //when
        List<Quiz> orderedQuizzes = quizRepository.findAllByOrderByTitleAsc();

        //then
        assertEquals("A quiz", orderedQuizzes.get(0).getTitle());
        assertEquals("T quiz", orderedQuizzes.get(1).getTitle());
        assertEquals("Z quiz", orderedQuizzes.get(2).getTitle());
        assertEquals(3, orderedQuizzes.size());
    }
}