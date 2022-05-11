/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestions;
import com.cmpe275.finalProject.cloudEventCenter.repository.SignUpForumQuestionsRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.SignUpForumQuestionsAnswersRepository;
import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestionsAnswers;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.HTTP_RESP;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;



/**
 * @author supreeth
 *
 */

@Service
@Component
public class SignUpForumService {
	
	@Autowired
	private SignUpForumQuestionsRepository questions_repository;
	
	@Autowired
	private SignUpForumQuestionsAnswersRepository answers_repository;
	
	@Autowired
	private EventRepository events_repository;
	
	@Autowired
	private UserRepository users_repository;
	
	@Transactional
	public ResponseEntity<?> createQuestion(
			String userId, 
			String eventId,
			String text
		) {
		
		try {
			
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
			// TODO: Validate event
			// TODO: Has the event deadline passed?
			// TODO: Is the event closed? 
			
			SignUpForumQuestions question = new SignUpForumQuestions(
					null,
					user,
					event,
					text,
					null,
					null
			);
			
			// TODO: What is the data type of the question
			SignUpForumQuestions savedQuestion = questions_repository.save(question);
						
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(savedQuestion);
				
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body("Internal Server Error");
		}
	}
	
	@Transactional
	public ResponseEntity<?> getQuestions(String eventId) {
		try {
			
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
			List<SignUpForumQuestions> questions = 
					questions_repository.findByEvent(event, Sort.by(Sort.Direction.DESC, "createdAt")); 
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
	                .body(questions);
				
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.toString());
		}
	}
	
	@Transactional
	public ResponseEntity<?> getQuestionAnswers(String questionId) {
		try {
			//TODO: Sort the data
			
			SignUpForumQuestions question = questions_repository.findById(questionId).orElse(null);
			if (question == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Sign Up Forum Question not found");
			}
			
			List<SignUpForumQuestionsAnswers> answers = answers_repository
					.findByQuestion(question, Sort.by(Sort.Direction.DESC, "createdAt")); 
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
	                .body(answers);
				
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.toString());
		}
	}
	
	@Transactional
	public ResponseEntity<?> createAnswer(String userId, String questionId, String text) {
		try {			
			User user = users_repository.findById(userId).orElse(null);
			if (user == null) {
				return ResponseEntity
			            .status(HttpStatus.UNAUTHORIZED)
			            .body("Unauthorized User");
			};
			
			SignUpForumQuestions question = questions_repository.findById(questionId).orElse(null);
			if (question == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Sign Up Forum Question not found");
			}
			
			String eventId = question.getEvent().getId();
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
			SignUpForumQuestionsAnswers answer = new SignUpForumQuestionsAnswers(
					null,
					user,
					eventId,
					question,
					text,
					null,
					null
			);
			
			// TODO: Validate event (
			// TODO: Has the event deadline passed?
			// TODO: Is the event closed?
			
			SignUpForumQuestionsAnswers savedAnswer = answers_repository.save(answer); 
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
	                .body(savedAnswer);
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(e.toString());
		}
	}
}
