/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestions;
import com.cmpe275.finalProject.cloudEventCenter.repository.ForumQuestionsRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.ForumQuestionsAnswersRepository;
import com.cmpe275.finalProject.cloudEventCenter.model.ForumQuestionsAnswers;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.amazonaws.HttpMethod;
import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;
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
	private ForumQuestionsRepository questions_repository;
	
	@Autowired
	private ForumQuestionsAnswersRepository answers_repository;
	
	@Autowired
	private EventRepository events_repository;
	
	@Autowired
	private UserRepository users_repository;
	
	@Autowired
	private AwsS3Service s3_service;
	
	private Boolean can_user_post(User user, Event event) {
		/**Becomes closed for posting of new messages 
			once the event registration deadline passes
				or 
			the event has been canceled*/
		if (!event.getStatus().equals(EEventStatus.REG_OPEN)) return false;
		if (event.getDeadline().isBefore(LocalDateTime.now())) return false;
		return true;
	};
	
	public ForumQuestions persist(User user, Event event, String text) {
		return questions_repository
				.save(
					new ForumQuestions(
							null,
							user,
							event,
							text,
							ForumTypes.SIGN_UP_FORUM,
							null,
							null
				));
	};
	
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
			
			if (!this.can_user_post(user, event)) {
				return ResponseEntity
			            .status(HttpStatus.FORBIDDEN)
			            .body("You are not permitted to do this action");
			};
			
			ForumQuestions savedQuestion = this.persist(user, event, text);
			
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
			
			List<ForumQuestions> questions = 
					questions_repository.findByEventAndForumType(
							event,
							ForumTypes.SIGN_UP_FORUM,
							Sort.by(Sort.Direction.DESC, "createdAt")); 
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
			
			ForumQuestions question = questions_repository.findById(questionId).orElse(null);
			if (question == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Forum Question not found");
			}
			
			List<ForumQuestionsAnswers> answers = answers_repository
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
			
			ForumQuestions question = questions_repository.findById(questionId).orElse(null);
			if (question == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Forum Question not found");
			}
			
			String eventId = question.getEvent().getId();
			Event event = events_repository.findById(eventId).orElse(null);
			if (event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			};
			
			if (!this.can_user_post(user, event)) {
				return ResponseEntity
			            .status(HttpStatus.FORBIDDEN)
			            .body("You are not permitted to do this action");
			};
			
			ForumQuestionsAnswers answer = new ForumQuestionsAnswers(
					null,
					user,
					eventId,
					question,
					text,
					null,
					null
			);
			
			ForumQuestionsAnswers savedAnswer = answers_repository.save(answer); 
			
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
	
	@Transactional
	public ResponseEntity<?> uploadImage(
			String userId,
			String questionId
	) {
		ForumQuestions question = questions_repository.findById(questionId).orElse(null);
		if (question == null) {
			return ResponseEntity
		            .status(HttpStatus.NOT_FOUND)
		            .body("Forum Question not found");
		}
		
		if (!userId.equals(question.getUser().getId())) {
			return ResponseEntity
		            .status(HttpStatus.FORBIDDEN)
		            .body("You are not permitted to perform this action");
		};
		
		String filePath = "cec/events/" + 
					question.getEvent().getId() + 
					"/forums/sign_up/" + 
					question.getId();
		
		// TODO: Bucket Name
		String bucketName = "us-east-2";
		String preSignedUrl = s3_service.generatePreSignedURL(
				filePath, 
				bucketName, 
				HttpMethod.PUT
		);
		
		HashMap<String, ArrayList<String>> response = new HashMap<>();
		ArrayList<String> urls = new ArrayList<String>();
		urls.add(preSignedUrl);
		response.put("data", urls);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	};
		
}
