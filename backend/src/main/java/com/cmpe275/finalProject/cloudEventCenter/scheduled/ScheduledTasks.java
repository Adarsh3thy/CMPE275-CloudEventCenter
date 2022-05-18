package com.cmpe275.finalProject.cloudEventCenter.scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cmpe275.finalProject.cloudEventCenter.model.EEventStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.MimicClockTime;
import com.cmpe275.finalProject.cloudEventCenter.model.ParticipantStatus;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventParticipantRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.EventRepository;
import com.cmpe275.finalProject.cloudEventCenter.controller.MimicClockTimeController;

@Component
public class ScheduledTasks {
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	EventParticipantRepository eventParticipantRepository;

	
	//@Scheduled(fixedDelay = 2000, initialDelay = 5000)
	public void performTask() {
		List<Event> allevents=eventRepository.findAll();
		LocalDateTime mimicDateTime=MimicClockTimeController.getMimicDateTime();
		LocalDateTime deadline;
		 int minParticipant;
		 System.out.println("In scheduled task "+mimicDateTime);
		for(Event event:allevents) {
			 deadline=event.getDeadline();
			 minParticipant=event.getMinParticipants();
			 List<EventParticipant> participants=event.getParticipants();
			 if(mimicDateTime.isAfter(deadline)  && participants.size()<minParticipant) {
				 System.out.println("Deadline has elapsed and less participants");
				 event.setStatus(EEventStatus.CANCELLED);
				 eventRepository.save(event);
				 for(EventParticipant participant:participants) {
					 participant.setStatus(ParticipantStatus.Cancelled);
					 eventParticipantRepository.save(participant);
				 }
			 }
		}
	}

}
