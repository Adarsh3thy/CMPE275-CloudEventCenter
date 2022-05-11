/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.time.LocalDateTime;
import java.util.Optional;

//import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.service.EventService;

/**
 * @author shrey
 *
 */

@RestController
@RequestMapping("/api")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserRepository userRepository;
	
	
//	@PreAuthorize("hasRole('ORGANIZER') or hasRole('PERSON')")
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> createEvent(@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam("startTime") String startTime,
			@RequestParam(value="endTime") String endTime,
			@RequestParam(value="deadline") String deadline,
			@RequestParam(value="street", required=false) String street,
			@RequestParam(value="number", required=false) String number,
			@RequestParam(value="city") String city,
			@RequestParam(value="state") String state,
			@RequestParam(value="zip") String zip,
			@RequestParam(value="minParticipants") int minParticipants, 
			@RequestParam(value="maxParticipants") int maxParticipants, 
			@RequestParam(value="fee") double fee, 
			@RequestParam(value="approvalReq") boolean approvalReq,
			@RequestParam(value="organizerID") String organizerID
			) {

		LocalDateTime converted_startTime = LocalDateTime.parse(startTime);
		LocalDateTime converted_endTime = LocalDateTime.parse(endTime);
		LocalDateTime converted_deadline = LocalDateTime.parse(deadline);
//		User eventOrganizer = userRepository.findById(organizerID).orElseGet(null);
		
		EventData newEvent = new EventData(title, description, converted_startTime, converted_endTime, converted_deadline, 
				street, number, city, state, zip, minParticipants, maxParticipants, fee, approvalReq, organizerID);
		
	    return eventService.addEvent(newEvent);
	  }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getEvent(@PathVariable("id") String id){
		return eventService.getEventByID(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/organizer/{organizerID}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> getAllEventsByOrganizerID(@PathVariable("organizerID") String organizerID){
		return eventService.getAllEventsByOrganizerID(organizerID);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/event/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> DeleteEvent(@PathVariable("eventID") String id){
		return eventService.cancelEvent(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/event/{page}/search")
	ResponseEntity<?> searchEvent(
			@PathVariable("page") int page,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String organizer
			
			){
		
		return eventService.searchEvent(city, status, startTime, endTime, keyword, organizer,page);
		
	}
//	@ResponseStatus(HttpStatus.OK)
//	@RequestMapping(value = "/event/{id}", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<?> getAllEventsByUserID(@PathVariable("userID") String id){
//		return eventService.getAllEventsByUserID(id);
//	}
}
