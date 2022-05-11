/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.MimicClockTime;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author shrey
 *
 */

@Service
@Component
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * This method is used to add an Event
	 * 
	 * @param title           title of the event
	 * @param description     description of the event
	 * @param startTime       startTime of the event
	 * @param endTime         endTime of the event
	 * @param deadline        deadline of the event
	 * @param address         address of the event
	 * @param minParticipants of the event
	 * @param maxParticipants of the event
	 * @param fee             of the event
	 * @return admissionPolicy of the event - can be either first-come-first-served,
	 *         or approval-required
	 */

	/*
	 * String title, String description, LocalDateTime startTime, LocalDateTime
	 * endTime, LocalDateTime deadline, String street, String number, String city,
	 * String state, String zip, int minParticipants, int maxParticipants, double
	 * fee, String admissionPolicy
	 */
	
	public static final int SEARCH_RESULT_PER_PAGE = 5;

	@Transactional
	public ResponseEntity<?> addEvent(EventData eventData) {
		try {
			Address address = new Address(eventData.getStreet(), eventData.getNumber(), eventData.getCity(),
					eventData.getState(), eventData.getZip());

			User user = userRepository.findById(eventData.getOrganizerID()).orElse(null);
			// Event Organizer field is failing, retest after UserController is completed
			// Switch eventData.getOrganizer() to null for successfull testing
			Event event = new Event(null, eventData.getTitle(), eventData.getDescription(), eventData.getStartTime(),
					eventData.getEndTime(), eventData.getDeadline(), eventData.getMinParticipants(),
					eventData.getMaxParticipants(), eventData.getFee(), false, user, address, new HashSet<User>(),
					EEventStatus.REG_OPEN, true);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(eventRepository.save(event));

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN addEvent EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}

	@Transactional
	public ResponseEntity<?> getEventByID(String id) {
		try {
			Event event = eventRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Invalid event ID"));

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(event);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN getEventByID EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}

	@Transactional
	public ResponseEntity<?> getAllEventsByOrganizerID(String organizerID) {
		try {
			User organizer = userRepository.getById(organizerID);

			List<Event> events = eventRepository.findByOrganizer(organizer);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(events);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN getEventsByOrganizerID EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}

//	@Transactional
//	public ResponseEntity<?> getAllEventsByUserID(String userID) {
//		try {
//
//			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("");
//
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//			System.out.println("IN getEventsByOrganizerID EXCEPTION BLOCK");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
//		}
//	}

	@Transactional
	public ResponseEntity<?> cancelEvent(String id) {
		try {
			Event event = eventRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Invalid event ID"));
			eventRepository.deleteById(id);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(event);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN cancelEvent EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}

	public ResponseEntity<?> searchEvent(String city, String status, String startTime, String endTime, String keyword,
			String organizer,int page) {

		int isActive = 0;
		String reqStatus = status;

		if (status == null) {
			isActive = 1;
			reqStatus = null;
		} else {

			if (status == "ACTIVE") {
				isActive = 1;
				reqStatus = null;
			} else if (status.equals("OPENFORREGISTRATION")) {
				isActive = 1;
				reqStatus = "REG_OPEN";
			} else if (status.equals("ALL")) {
				isActive = 0;
				reqStatus = null;
			}
		}
		
		ZoneId zoneSingapore = ZoneId.of("America/Los_Angeles");  
		String mimicDateTime= MimicClockTime.getCurrentTime().instant().atZone(zoneSingapore).toString();
		String mimicDate=mimicDateTime.substring(0,mimicDateTime.indexOf('T'));
		String mimicTime=mimicDateTime.substring(mimicDateTime.indexOf('T')+1,
				mimicDateTime.lastIndexOf('-')-4);
		String ConvertedDateTime=mimicDate+" "+mimicTime;
		System.out.println("ConvertedDateTime: "+ConvertedDateTime);
		
		  System.out.println("keyword: "+keyword);
		  System.out.println("isActive: "+isActive);
		  System.out.println("startTime: "+startTime);
		  System.out.println("status: "+reqStatus);
		 

		
		
		//List<Event> searchedEvents=eventRepository.searchForEvents(keyword, city, status, ConvertedDateTime, startTime, endTime, isActive);
		
		Pageable pageable = PageRequest.of(page - 1, SEARCH_RESULT_PER_PAGE); 
		Page<Event> searchedEvents=eventRepository.searchForEvents( keyword,city,reqStatus,ConvertedDateTime,startTime,endTime,isActive,organizer,pageable);

		
		System.out.println(searchedEvents);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(searchedEvents);

	}
}
