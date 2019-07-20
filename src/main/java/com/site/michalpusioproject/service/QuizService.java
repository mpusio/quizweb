package com.site.michalpusioproject.service;

import com.site.michalpusioproject.domains.Answer;
import com.site.michalpusioproject.domains.Question;
import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuizService {

    private QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllOrderedQuizzes(){
        return quizRepository.findAllByOrderByTitleAsc();
    }

    public Optional<Quiz> getQuizById(Long id){
        return quizRepository.findById(id);
    }

    public void deleteQuizById(Long id){
        quizRepository.deleteById(id);
    }

    public void insertOrUpdateQuiz(Quiz quiz){
        quizRepository.save(quiz);
    }

    public String matchQuizAnswers(Quiz quiz, Quiz secondQuiz) {
        int result = 0;
        int max = 0;


        List<Question> correctQuestions = secondQuiz.getQuestions();
        List<Question> solvedQuestions = quiz.getQuestions();

        for (int i = 0; i < solvedQuestions.size(); i++) {
            List<Answer> correctAnswers = correctQuestions.get(i).getAnswers().stream()
                    .filter(answer -> Boolean.toString(answer.getIsCorrect()).equals("true"))
                    .collect(Collectors.toList());

            List<Answer> checkedAnswers = solvedQuestions.get(i).getAnswers().stream()
                    .filter(answer -> Boolean.toString(answer.getIsCorrect()).equals("true"))
                    .collect(Collectors.toList());

            if (correctAnswers.equals(checkedAnswers)){
                result += 1;
            }
            max++;
        }
        return result + "/" + max;
    }
}
