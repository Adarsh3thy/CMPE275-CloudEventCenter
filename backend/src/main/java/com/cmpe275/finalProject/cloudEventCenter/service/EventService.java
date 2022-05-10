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
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
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
			Address address = new Address(eventData.getStreet(), eventData.getNumber(), eventData.getCity(),
					eventData.getState(), eventData.getZip());

			User user = userRepository.findById(eventData.getOrganizerID()).orElse(null);
			// Event Organizer field is failing, retest after UserController is completed
			// Switch eventData.getOrganizer() to null for successfull testing
			Event event = new Event(null, eventData.getTitle(), eventData.getDescription(), eventData.getStartTime(),
					eventData.getEndTime(), eventData.getDeadline(), eventData.getMinParticipants(),
					eventData.getMaxParticipants(), eventData.getFee(), false, user, address, new HashSet<User>());

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
}
