package com.site.michalpusioproject.service;

import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.domains.Result;
import com.site.michalpusioproject.domains.User;
import com.site.michalpusioproject.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    private ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public void addResult(Quiz quiz, User user, int points){
        Optional<Result> result = resultRepository.findByUserAndQuiz(user, quiz);
        if (result.isPresent()){
            Result existResult = result.get();
            int theBestResult = Math.max(points, existResult.getAchieved_points());
            existResult.setAchieved_points(theBestResult);

            resultRepository.save(existResult);
        }
        else {
            resultRepository.save(new Result(points, quiz, user));
        }

    }

    public void updateResult (Quiz quiz, User user, int points) {
        resultRepository.updateByUserIdAndQuizId(points, user.getId(), quiz.getId());
    }

    public List<Result> findResult (Quiz quiz, User user){
        return resultRepository.findByUserIdAndQuizId(user.getId(), quiz.getId());
    }

    public List<Result> findAllResultsByUser (User user){
        Long id = user.getId();
        return resultRepository.findAllByUserId(id);
    }
}
