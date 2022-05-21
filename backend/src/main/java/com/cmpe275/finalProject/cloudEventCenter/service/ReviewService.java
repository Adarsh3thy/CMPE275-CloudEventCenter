package com.cmpe275.finalProject.cloudEventCenter.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventRole;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.MimicClockTime;
import com.cmpe275.finalProject.cloudEventCenter.model.Reviews;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.ReviewsRepository;

@Service
@Component
public class ReviewService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private ReviewsRepository reviewsRepository;
	
	public ResponseEntity<?> addReview(@Valid String eventID, String reviewerID, String review, int rating) {
		try {
			Event event = eventRepository.getById(eventID);
			User organizer = event.getOrganizer();
			String organizerID = organizer.getId();
			
			if(reviewsRepository.findByReviewerAndReviewForAndReviewTypeAndEventId(reviewerID, organizerID, EEventRole.ORGANIZER, eventID).size() > 0)
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("You have already reviewed/rated");
			
			ZoneId zoneSingapore = ZoneId.of("America/Los_Angeles");  
			String mimicDateTime= MimicClockTime.getCurrentTime().instant().atZone(zoneSingapore).toString();
			String mimicDate=mimicDateTime.substring(0,mimicDateTime.indexOf('T'));
			String mimicTime=mimicDateTime.substring(mimicDateTime.indexOf('T')+1, mimicDateTime.lastIndexOf('-')-4);
			String ConvertedDateTime=mimicDate+"T"+mimicTime;
			
			LocalDateTime currDateTime = LocalDateTime.parse(ConvertedDateTime);
			
			Reviews newReview = new Reviews(null, reviewerID, organizerID, eventID, EEventRole.ORGANIZER, review, rating, currDateTime);
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(reviewsRepository.save(newReview));
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN addReview EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}

}
