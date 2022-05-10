package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.service.SignUpForumService;

/**
 * 
 * @author supreeth
 *
 * Sign Up Forum
 * ALL users can post a question on the sign up forum
 * ALL users can reply to a question on the sign up forum
 */

@RestController
public class SignUpForumController {
	
	String USER_ID_WHICH_SHOULD_NOT_BE_INJECTED = "1";
	String EVENT_ID_WHICH_SHOULD_NOT_BE_INJECTED = "420";
	
	@Autowired
	private SignUpForumService signUpForumService;
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up_forum/{eventId}/questions", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> createSignUpForumQuestion(
			@PathVariable(value =  "eventId") String eventId,
			@RequestParam("text") String text
	) {	
			return signUpForumService.createQuestion(this.USER_ID_WHICH_SHOULD_NOT_BE_INJECTED, this.EVENT_ID_WHICH_SHOULD_NOT_BE_INJECTED, text);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up_forum/{eventId}/questions/{questionId}/answers", 
			method = RequestMethod.POST, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> createAnswer(
			@PathVariable(value =  "eventId") String eventId,
			@PathVariable(value =  "questionId") String questionId,
			@RequestParam("text") String text
	) {
			return signUpForumService.createAnswer(this.USER_ID_WHICH_SHOULD_NOT_BE_INJECTED, this.EVENT_ID_WHICH_SHOULD_NOT_BE_INJECTED, questionId, text);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up_forum/{eventId}/questions", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getSignUpForumQuestions(
			@PathVariable(value =  "eventId") String eventId
	) {
			return signUpForumService.getQuestions(this.EVENT_ID_WHICH_SHOULD_NOT_BE_INJECTED);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/sign_up_forum/{eventId}/questions/{questionId}/answers", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE
	)
	ResponseEntity<?> getSignUpForumQuestionsAnswers(
			@PathVariable(value =  "eventId") String eventId,
			@PathVariable(value =  "questionId") String questionId
	) {
			return signUpForumService.getQuestionAnswers(this.EVENT_ID_WHICH_SHOULD_NOT_BE_INJECTED, questionId);
    }
}
