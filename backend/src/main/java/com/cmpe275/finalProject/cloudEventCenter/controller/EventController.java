/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.time.LocalDateTime;

//import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.service.EventService;

/**
 * @author shrey
 *
 */

@RestController
public class EventController {
	
	@Autowired
	private EventService eventService;
	
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
			@RequestParam(value="approvalReq") boolean approvalReq
			) {
		
		System.out.println("reached");
		
		LocalDateTime converted_startTime = LocalDateTime.parse(startTime);
		LocalDateTime converted_endTime = LocalDateTime.parse(endTime);
		LocalDateTime converted_deadline = LocalDateTime.parse(deadline);
		
//		EventData newEvent = new EventData();
		
		EventData newEvent = new EventData(title, description, converted_startTime, converted_endTime, converted_deadline, 
				street, number, city, state, zip, minParticipants, maxParticipants, fee, approvalReq);
		
	    return eventService.addEvent(newEvent);
	  }
}
