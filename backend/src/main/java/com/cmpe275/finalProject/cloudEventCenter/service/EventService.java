/**
 * 
 */
package com.cmpe275.finalProject.cloudEventCenter.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;

/**
 * @author shrey
 *
 */

@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	/**
	   * This method is used to add an Event
	   * @param title title of the event
	   * @param description description of the event
	   * @param startTime startTime of the event
	   * @param endTime endTime of the event
	   * @param deadline deadline of the event
	   * @param address address of the event
	   * @param minParticipants of the event
	   * @param maxParticipants of the event
	   * @param fee of the event
	   * @return admissionPolicy of the event - can be either first-come-first-served, or approval-required
	   */
	
	/*String title,
			String description, LocalDateTime startTime, LocalDateTime endTime,
			LocalDateTime deadline, String street, String number, String city, String state,
			String zip, int minParticipants, int maxParticipants, double fee, String admissionPolicy*/
	
	@Transactional
	public ResponseEntity<?> addEvent(EventData eventData) {
		try {
				Address address = new Address(eventData.getStreet(), eventData.getNumber(), eventData.getCity(), 
						eventData.getState(), eventData.getZip());
				
				Event event = new Event(null, eventData.getTitle(), eventData.getDescription(), eventData.getStartTime(), 
						eventData.getEndTime(), eventData.getDeadline(), eventData.getMinParticipants(), 
						eventData.getMaxParticipants(), eventData.getFee(), false, 
						null, address);

				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
	                    .body(eventRepository.save(event));
				
		}catch(Exception e) {
			e.printStackTrace(System.out);
			System.out.println("IN EXCEPTION BLOCK");
			return ResponseEntity
		            .status(HttpStatus.BAD_REQUEST)
		            .body(e.toString());
		}
	}
}
