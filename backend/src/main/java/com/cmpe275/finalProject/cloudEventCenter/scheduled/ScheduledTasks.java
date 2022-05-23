package com.cmpe275.finalProject.cloudEventCenter.scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventParticipantRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.controller.MimicClockTimeController;
import com.cmpe275.finalProject.cloudEventCenter.mail.service.NotificationMailService;

@Component
public class ScheduledTasks {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EventParticipantRepository eventParticipantRepository;
	
	@Autowired
	NotificationMailService notificationMailService;

	
	@Scheduled(fixedDelay = 2000, initialDelay = 5000)
	public void cancelEvent() {
		List<Event> allEvents=eventRepository.findAll();
		LocalDateTime mimicDateTime=MimicClockTimeController.getMimicDateTime();

		 
		 List<Event> eventsToBeCancelled = allEvents.stream().filter(event -> event.getDeadline().isBefore(mimicDateTime)
				 && event.getParticipants().size()<event.getMinParticipants()
				 && !event.getStatus().equals(EEventStatus.CANCELLED))
					.collect(Collectors.toList());
		 
		 for(Event event:eventsToBeCancelled) {
			 event.setStatus(EEventStatus.CANCELLED);
			 event.setActive(false);
			 eventRepository.save(event);
			 HashMap<String,String> params=new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			 for(EventParticipant participant:event.getParticipants()) {
				 	User user=userRepository.getById(participant.getId().getParticipantId());
				 	
					notificationMailService.sendNotificationEmail(user.getEmail(), "eventCancel", params);
			 }
		 }
		 
		 
		
	}

}
