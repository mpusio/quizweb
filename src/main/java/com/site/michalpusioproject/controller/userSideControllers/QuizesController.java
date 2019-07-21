package com.site.michalpusioproject.controller.userSideControllers;

import com.site.michalpusioproject.domains.*;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.ResultService;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class QuizesController {

    private QuizService quizService;
    private ResultService resultService;
    private UserService userService;

    @Autowired
    public QuizesController(QuizService quizService, ResultService resultService, UserService userService) {
        this.quizService = quizService;
        this.resultService = resultService;
        this.userService = userService;
    }

    @GetMapping("/quizzes")
    public String getQuizzesPage(Model model, Authentication authentication){
        User foundedUserByEmail = userService.getUserByEmail(authentication.getName());

        model.addAttribute("quizzes", quizService.getAllOrderedQuizzes());
        model.addAttribute("results", resultService.findAllResultsByUser(foundedUserByEmail));
        return "user/quizzes";
    }

    @GetMapping("/quizzes/{id}/solution")
    public String getQuizByIdToSolve (Model model, @PathVariable Long id){
        Quiz quiz = quizService.getQuizById(id).get();
        //set all answers on false
        List<Question> questions = quiz.getQuestions();
        for (Question q:questions) {
            q.getAnswers().forEach(a -> a.setIsCorrect(false));
        }
        quiz.setQuestions(questions);

        model.addAttribute("quiz", quiz);
        return "user/solution";
    }

    @PostMapping("/quizzes/{id}/solution")
    public String checkAnswersAndReturnResult (Model model, @Valid Quiz solvedQuiz, @PathVariable Long id, Authentication authentication){
        Quiz quizWithCorrectAnswers = quizService.getQuizById(id).get();
        String result = quizService.matchQuizAnswers(solvedQuiz, quizWithCorrectAnswers);

        int achievedPoints = Integer.valueOf(result.split("/")[0]);
        User actualUser = userService.getUserByEmail(authentication.getName());

        resultService.addOrUpdateResult(solvedQuiz, actualUser, achievedPoints);

        model.addAttribute("result", result);
        return "user/result";
    }

    @GetMapping("/quizzes/{id}/result")
    public String getResultPage(@PathVariable Long id){
        return "user/result";
    }

}
