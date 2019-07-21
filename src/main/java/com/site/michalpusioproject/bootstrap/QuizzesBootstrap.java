//package com.site.michalpusioproject.bootstrap;
//
///* Some added data to start */
//
//import com.site.michalpusioproject.domains.Answer;
//import com.site.michalpusioproject.domains.Question;
//import com.site.michalpusioproject.domains.Quiz;
//import com.site.michalpusioproject.repository.QuizRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class QuizzesBootstrap {
//
//    private QuizRepository quizRepository;
//
//    @Autowired
//    public QuizzesBootstrap(QuizRepository quizRepository) {
//        this.quizRepository = quizRepository;
//    }
//
//    @Bean
//    private void initAllData(){
//        quizRepository.saveAll(initQuizData());
//    }
//
//    private List<Answer> initAnswersToChampionsLeagueQuestion1(){
//        Answer answers1 = new Answer("Liverpool");
//        Answer answers2 = new Answer("Barcelona");
//        Answer answers3 = new Answer("Real Madryt");
//        Answer answers4 = new Answer("Chelsea");
//
//        answers3.setIsCorrect(true);
//
//        return new ArrayList<>( Arrays.asList(answers1, answers2, answers3, answers4) );
//    }
//
//    private List<Answer> initAnswersToChampionsLeagueQuestion2(){
//        Answer answers1 = new Answer("Lionel Messi");
//        Answer answers2 = new Answer("Cristiano Ronaldo");
//        Answer answers3 = new Answer("Zinedine Zidan");
//        Answer answers4 = new Answer("Grzegorz Lato");
//
//        answers2.setIsCorrect(true);
//
//        return new ArrayList<>( Arrays.asList(answers1, answers2, answers3, answers4) );
//    }
//
//    private List<Answer> initAnswersToPremierLeagueQuestion1(){
//        Answer answers1 = new Answer("Paul Pogba");
//        Answer answers2 = new Answer("Fernando Torres");
//        Answer answers3 = new Answer("Eden Hazard");
//        Answer answers4 = new Answer("Vincent Kompany");
//
//        answers1.setIsCorrect(true);
//
//        return new ArrayList<>( Arrays.asList(answers1, answers2, answers3, answers4) );
//    }
//
//    private List<Answer> initAnswersToPremierLeagueQuestion2(){
//        Answer answers1 = new Answer("Mauricio Sarri");
//        Answer answers2 = new Answer("Jurgen Klopp");
//        Answer answers3 = new Answer("Sir Alex Ferguson");
//        Answer answers4 = new Answer("Pep Guardiola");
//
//        answers4.setIsCorrect(true);
//
//        return new ArrayList<>( Arrays.asList(answers1, answers2, answers3, answers4) );
//    }
//
//
//    private List<Question> initChampionsLeagueQuestions(){
//        Question question1 = new Question("Who has won the most Champions League titles");
//        Question question2 = new Question("Who has scored the most goals in UEAF Champions League?");
//
//        question1.setAnswers(initAnswersToChampionsLeagueQuestion1());
//        question2.setAnswers(initAnswersToChampionsLeagueQuestion2());
//
//        return new ArrayList<>(Arrays.asList(question1, question2));
//    }
//
//    private List<Question> initPremierLeagueQuestions(){
//        Question question1 = new Question("Who is the most expensive player actually in Premier League?");
//        Question question2 = new Question("Who was the trainer Manchester City in season 2018/2019?");
//
//        question1.setAnswers(initAnswersToPremierLeagueQuestion1());
//        question2.setAnswers(initAnswersToPremierLeagueQuestion2());
//
//        return new ArrayList<>(Arrays.asList(question1, question2));
//    }
//
//
//    private List<Quiz> initQuizData(){
//        Quiz championsLeagueQuiz = new Quiz("Champions League Quiz");
//        championsLeagueQuiz.setDescription("Check your knowledge about Champions League!");
//        championsLeagueQuiz.setQuestions(initChampionsLeagueQuestions());
//
//        Quiz premierLeagueQuiz = new Quiz("Premier League Quiz");
//        premierLeagueQuiz.setDescription("How well do you know the Premier League? Check yourself now!");
//        premierLeagueQuiz.setQuestions(initPremierLeagueQuestions());
//
//        return new ArrayList<>(Arrays.asList(championsLeagueQuiz, premierLeagueQuiz));
//    }
//
//}
