/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.cmpe275.finalProject.cloudEventCenter.model.MimicClockTime;
import com.cmpe275.finalProject.cloudEventCenter.model.ParticipantStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Role;

import org.springframework.web.bind.annotation.RequestParam;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.ERole;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipentId;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventParticipantRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;

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
	
	@Autowired
	private EventParticipantRepository eventParticipantRepository;
	
	public static final int SEARCH_RESULT_PER_PAGE = 5;

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

	@Transactional
	public ResponseEntity<?> addEvent(EventData eventData) {
		try {
			if(eventData.getTitle().isBlank() || eventData.getDescription().isBlank()) {
//				throw new IllegalArgumentException("Enter a valid title and desciption");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a valid title and desciption");
			}
			
			if(eventData.getStartTime() == null || eventData.getEndTime() == null){
//				throw new IllegalArgumentException("Enter a valid startTime/endTime");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a valid startTime/endTime");
			}
			
			if(eventData.getEndTime().isBefore(eventData.getStartTime()) || eventData.getEndTime().isEqual(eventData.getStartTime())) {
//				throw new IllegalArgumentException("Enter a startTime before endTime");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a startTime before endTime");
			}
				
			if(eventData.getStartTime().isBefore(eventData.getDeadline())) {
//				throw new IllegalArgumentException("Enter a startTime after deadline");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a startTime after deadline");
			}
			
			if(eventData.getAddress().getCity().isBlank() || eventData.getAddress().getState().isBlank() || eventData.getAddress().getZip().isBlank()) {
//				throw new IllegalArgumentException("Enter a city, state and zipcode for the event");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a city, state and zipcode for the event");
			}
			
			if(eventData.getMinParticipants() < 0) {
//				throw new IllegalArgumentException("Enter a positive minimum number of participants");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a positive minimum number of participants");
			}
			
			if(eventData.getMaxParticipants() > Integer.MAX_VALUE) {
//				throw new IllegalArgumentException("Enter a valid number of max participants");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter a valid number of max participants");
			}
			
			User organizer  = userRepository.findById(eventData.getOrganizerID()).orElse(null);
			Role organizerRole = organizer.getRoles().iterator().next();
			
			if(eventData.getFee() > 0 && organizerRole.getName().equals(ERole.ROLE_PERSON)) {
//				throw new IllegalArgumentException("Participant cant charge a fee");
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Participant cant charge a fee");
			}
			
			ZoneId zoneSingapore = ZoneId.of("America/Los_Angeles");  
			String mimicDateTime= MimicClockTime.getCurrentTime().instant().atZone(zoneSingapore).toString();
			String mimicDate=mimicDateTime.substring(0,mimicDateTime.indexOf('T'));
			String mimicTime=mimicDateTime.substring(mimicDateTime.indexOf('T')+1, mimicDateTime.lastIndexOf('-')-4);
			String ConvertedDateTime=mimicDate+"T"+mimicTime;
			
			LocalDateTime currDateTime = LocalDateTime.parse(ConvertedDateTime);
			
			if(currDateTime.isAfter(eventData.getDeadline()) || currDateTime.isAfter(eventData.getStartTime()) || currDateTime.isAfter(eventData.getEndTime())) {
				return ResponseEntity.badRequest().body(new MessageResponse("You cannot create an event with deadline, startTime, or endTime in the past"));
			}
			
			Address address = eventData.getAddress();

			User user = userRepository.findById(eventData.getOrganizerID()).orElse(null);
			
			if(user == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("User not found");
			}
			
			Event event = new Event(null, eventData.getTitle(), eventData.getDescription(), eventData.getStartTime(),
					eventData.getEndTime(), eventData.getDeadline(), eventData.getMinParticipants(),
					eventData.getMaxParticipants(), eventData.getFee(), false, user, address, null,
					EEventStatus.REG_OPEN, true);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(eventRepository.save(event));

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN addEvent EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}

	@Transactional
	public ResponseEntity<?> getEventByID(String id) {
		try {
			if(id.isBlank()) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter an event ID");
			}
			Event event = eventRepository.findById(id).orElse(null);
			
			if(event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			}
			

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(event);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN getEventByID EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}

	@Transactional
	public ResponseEntity<?> getAllEventsByOrganizerID(String organizerID) {
		try {
			if(organizerID.isBlank()) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter an organizerID");
			}
			User organizer = userRepository.findById(organizerID).orElse(null);
			
			if(organizer == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("organizer not found");
			}		
			
			
			List<Event> events = eventRepository.findByOrganizer(organizer);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(events);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN getEventsByOrganizerID EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}

	@Transactional
	public ResponseEntity<?> cancelEvent(String id) {
		try {
			if(id.isBlank()) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Enter an event ID");
			}
			
			Event event = eventRepository.findById(id).orElse(null);
			
			if(event == null) {
				return ResponseEntity
			            .status(HttpStatus.NOT_FOUND)
			            .body("Event not found");
			}
			
			eventRepository.deleteById(id);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(event);

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN cancelEvent EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
	}
	
	public ResponseEntity<?> addParticipant(String eventID, String userID) {
		try {
			
			Event event = eventRepository.getById(eventID);
			if(event==null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid event ID!"));

			}
			
			ZoneId zoneSingapore = ZoneId.of("America/Los_Angeles");  
			String mimicDateTime= MimicClockTime.getCurrentTime().instant().atZone(zoneSingapore).toString();
			String mimicDate=mimicDateTime.substring(0,mimicDateTime.indexOf('T'));
			String mimicTime=mimicDateTime.substring(mimicDateTime.indexOf('T')+1, mimicDateTime.lastIndexOf('-')-4);
			String ConvertedDateTime=mimicDate+"T"+mimicTime;
			
			LocalDateTime currDateTime = LocalDateTime.parse(ConvertedDateTime);
			
			if(currDateTime.isAfter(event.getDeadline())) {
				return ResponseEntity.badRequest().body(new MessageResponse("You cant register after deadline has passed"));
			}
			
			User user=userRepository.getById(userID);
			if(user==null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid user ID!"));

			}
			if(event.getOrganizer().getId().equals(userID)) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: event organizer cant be participant too!"));

			}
			
			if(event.getMaxParticipants()==event.getParticipants().size()) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: max limit for the event reached!"));

			}
			
			EventParticipant eventParticipant=new EventParticipant();
			EventParticipentId eventParticipantId=new EventParticipentId(event.getId(),user.getId());
			eventParticipant.setId(eventParticipantId);
			eventParticipant.setEvent(event);
			eventParticipant.setParticipant(user);
			eventParticipant.setStatus(ParticipantStatus.Pending);
			
			EventParticipant reteventParticipant=eventParticipantRepository.save(eventParticipant);
			
		/*	Event event = eventRepository.getById(eventID);
			List<User> participants = event.getParticipants();
//			System.out.println("aaa" + participants.size());
			participants.add(userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Invalid User ID")));
			event.setParticipants(participants);
			eventRepository.save(event);
//			System.out.println("aaa" + event.getParticipants().size());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(event);
		*/
			
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(reteventParticipant);	
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN addParticipant EXCEPTION BLOCK");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
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
