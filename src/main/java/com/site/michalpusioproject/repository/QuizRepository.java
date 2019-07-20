package com.site.michalpusioproject.repository;

import com.site.michalpusioproject.domains.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByOrderByTitleAsc();
}
