package com.cmpe275.finalProject.cloudEventCenter.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.ParticipantForum;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.ParticipantForumRepository;


@Service
public class ParticipantForumService {
	
	@Autowired
	private ParticipantForumRepository pForumRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	public void createpForum(Event event) {
		if (!pForumRepository.existsByEvent(event)){
			ParticipantForum pforum=new ParticipantForum();
			pforum.setEvent(event);
			pforum.setReadOnly(false);
			pforum.setStatus("OPEN");
			Set<User> participants=new HashSet<>();
			participants.add(event.getOrganizer());
			pforum.setUsers(participants); //needs to be changed
		}
				
	}
	
	public ResponseEntity<?> getUsers(String eventId){
		if (eventRepository.findById(eventId).get() == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid event ID!"));
		}
		
		Event event=eventRepository.findById(eventId).get();
		
		if(!pForumRepository.existsByEvent(event)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Participant forum is not yet available for the event!"));
		}
		
		return ResponseEntity.ok( pForumRepository.findByEvent(event));
		
	}
	
	
	
}
