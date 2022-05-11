package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestionsAnswers;

public interface SignUpForumQuestionsAnswersRepository extends JpaRepository<SignUpForumQuestionsAnswers, String>{
}