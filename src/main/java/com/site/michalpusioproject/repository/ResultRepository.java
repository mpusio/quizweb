package com.site.michalpusioproject.repository;

import com.site.michalpusioproject.domains.Quiz;
import com.site.michalpusioproject.domains.Result;
import com.site.michalpusioproject.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query(value = "INSERT INTO result VALUES (?1, ?2, ?3)", nativeQuery = true)
    void mySave(Integer point, Long quidId, Long userId);

    @Query(value = "SELECT * FROM RESULT WHERE user_id=?1", nativeQuery = true)
    List<Result> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM RESULT WHERE user_id=?1 AND quiz_id=?2", nativeQuery = true)
    List<Result> findByUserIdAndQuizId(Long userId, Long quizId);

    @Query(value = "UPDATE result SET achieved_points=?1 WHERE quiz_id=?2 AND user_id==?3", nativeQuery = true)
    List<Result> updateByUserIdAndQuizId(Integer point, Long quizId, Long userId);

    Optional<Result> findByUserAndQuiz(User user, Quiz quiz);


}
