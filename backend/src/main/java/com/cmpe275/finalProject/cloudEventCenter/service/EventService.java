/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

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

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
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
			Address address = eventData.getAddress();

			User user = userRepository.findById(eventData.getOrganizerID()).orElse(null);
			// Event Organizer field is failing, retest after UserController is completed
			// Switch eventData.getOrganizer() to null for successfull testing
		
			
			Event event = new Event(null, eventData.getTitle(), eventData.getDescription(), eventData.getStartTime(),
					eventData.getEndTime(), eventData.getDeadline(), eventData.getMinParticipants(),
					eventData.getMaxParticipants(), eventData.getFee(), false, user, address, null,
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
	
	public ResponseEntity<?> addParticipant(String eventID, String userID) {
		try {
			
			Event event = eventRepository.getById(eventID);
			if(event==null) {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid event ID!"));

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
			eventParticipant.setStatus("REGISTERED");
			
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
		}
	}
}
