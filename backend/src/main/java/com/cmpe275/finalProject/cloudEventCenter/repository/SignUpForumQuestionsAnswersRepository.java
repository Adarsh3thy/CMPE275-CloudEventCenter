package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestions;
import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestionsAnswers;

public interface SignUpForumQuestionsAnswersRepository extends JpaRepository<SignUpForumQuestionsAnswers, String>{
	public List<SignUpForumQuestionsAnswers> findByQuestion(SignUpForumQuestions question, Sort sort);
}
