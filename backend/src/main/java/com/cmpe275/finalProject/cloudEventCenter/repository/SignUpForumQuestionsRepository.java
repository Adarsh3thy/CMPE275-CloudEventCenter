package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestions;

public interface SignUpForumQuestionsRepository extends JpaRepository<SignUpForumQuestions, String>{
	public List<SignUpForumQuestions> findByEvent(Event event, Sort sort);
}
