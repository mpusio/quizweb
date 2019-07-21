package com.site.michalpusioproject.controller.adminSideControllers;

import com.site.michalpusioproject.domains.Answer;
import com.site.michalpusioproject.domains.Question;
import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.service.QuizService;
import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;


@Controller
@RequestMapping(value = "/admin")
public class QuizController {

    private QuizService quizService;
    private UserService userService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addQuizzesAttribute(Model model){
        model.addAttribute("quizzes", quizService.getAllOrderedQuizzes());
        model.addAttribute("users", userService.getAllUsers());
    }

    @GetMapping("/quizzes")
    public String getOrderedQuizzes() {
        return "admin/quizzes/quizzes";
    }

    @GetMapping("/quizzes/{id}/view")
    public String getQuizById(Model model, @PathVariable Long id){
        model.addAttribute("quiz", quizService.getQuizById(id).get());
        return "admin/quizzes/quiz_view";
    }

    @GetMapping("/quizzes/{id}/edit")
    public String editQuizById(Model model, @PathVariable Long id){
        model.addAttribute("quiz", quizService.getQuizById(id).get());
        return "admin/quizzes/quiz_form";
    }

    @GetMapping("/quizzes/{id}/delete")
    public String deleteQuizById(@PathVariable Long id){
        quizService.deleteQuizById(id);
        return "redirect:/admin/quizzes";
    }


    @GetMapping("/quizzes/form")
    public String getFormPage(Model model) {
        Quiz quiz = new Quiz();
        Question question = new Question();
        Answer answer = new Answer();

        question.setAnswers(new ArrayList<>());
        question.getAnswers().add(answer);

        quiz.setQuestions(new ArrayList<>());
        quiz.getQuestions().add(question);

        model.addAttribute("quiz", quiz);

        return "admin/quizzes/quiz_form";
    }


    @PostMapping(value={"/quizzes/form", "/quizzes/{id}/edit"})
    public String addQuiz(@ModelAttribute @Valid Quiz quiz){
        quizService.insertOrUpdateQuiz(quiz);
        return "redirect:/admin/quizzes";
    }

    @PostMapping(value={"/quizzes/form", "/quizzes/{id}/edit"}, params={"addQuestion"})
    public String addQuestion(@ModelAttribute("quiz") Quiz quiz, BindingResult bindingResult) {
        Question question = new Question();
        question.setAnswers(new ArrayList<>());
        question.getAnswers().add(new Answer());
        quiz.getQuestions().add(question);
        return "admin/quizzes/quiz_form";
    }

    @PostMapping(value={"/quizzes/form", "/quizzes/{id}/edit"}, params={"removeQuestion"})
    public String removeQuestion(Quiz quiz, BindingResult bindingResult, HttpServletRequest req) {
        final Integer questionId = Integer.valueOf(req.getParameter("removeQuestion"));
        quiz.getQuestions().remove(questionId.intValue());
        return "admin/quizzes/quiz_form";
    }

    @PostMapping(value={"/quizzes/form", "/quizzes/{id}/edit"}, params={"addAnswer"})
    public String addAnswer(Quiz quiz, Question question, BindingResult bindingResult, HttpServletRequest req) {
        final Integer questionId = Integer.valueOf(req.getParameter("addAnswer"));
        quiz.getQuestions().get(questionId).getAnswers().add(new Answer());
        return "admin/quizzes/quiz_form";
    }

    @PostMapping(value={"/quizzes/form", "/quizzes/{id}/edit"}, params={"removeAnswer"})
    public String removeAnswer(Quiz quiz,  BindingResult bindingResult, HttpServletRequest req) {
        String[] answerIdAndQuestionId = req.getParameter("removeAnswer").split("-");

        final Integer answerId = Integer.valueOf(answerIdAndQuestionId[0]);
        final Integer questionId = Integer.valueOf(answerIdAndQuestionId[1]);

        quiz.getQuestions().get(questionId).getAnswers().remove(answerId.intValue());

        return "admin/quizzes/quiz_form";
    }
}
