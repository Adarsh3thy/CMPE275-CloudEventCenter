package com.cmpe275.finalProject.cloudEventCenter.scheduled;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	
	@Autowired
	TransactionalScheduledService transactionalScheduledService;
	
	Set<String> logCancelledEvents=new HashSet<>();
	Set<String> logRegClosedEvents=new HashSet<>();
	Set<String> logStartedEvents=new HashSet<>();
	Set<String> logDoneEvents=new HashSet<>();
	Set<String> logForumOpenEvents=new HashSet<>();
	Set<String> logForumCloseEvents=new HashSet<>();

	@Scheduled(fixedDelay = 2000, initialDelay = 5000)
	public void cancelEvent() {
		List<Event> allEvents = eventRepository.findAll();
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		
		
		List<Event> eventsToBeCancelled = allEvents.stream()
				.filter(event -> event.getDeadline().isBefore(mimicDateTime)
						&& event.getParticipants().size() < event.getMinParticipants()
						&& !event.getStatus().equals(EEventStatus.CANCELLED))
				.collect(Collectors.toList());
		for (Event event : eventsToBeCancelled) {
			if(logCancelledEvents==null ||( logCancelledEvents!=null && !logCancelledEvents.contains(event.getId()))) {

			transactionalScheduledService.updateCancelledEventStatus(event);
			HashMap<String, String> params = new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			for (EventParticipant participant : event.getParticipants()) {
				User user = userRepository.getById(participant.getId().getParticipantId());

				notificationMailService.sendNotificationEmail(user.getEmail(), "eventCancel", params);
			}
			logCancelledEvents.add(event.getId());
		}
		}

	}
	
	@Scheduled(fixedDelay = 2000, initialDelay = 1500)
	public void maxRegistrations() {

		List<Event> allEvents = eventRepository.findAll();
		
		List<Event> eventsRegsToBeClosed = allEvents.stream()
				.filter(event -> event.getParticipants().size() >= event.getMaxParticipants()
						&& !event.getStatus().equals(EEventStatus.REG_CLOSED)
						&& event.getStatus().equals(EEventStatus.REG_OPEN))
				.collect(Collectors.toList());
		for (Event event : eventsRegsToBeClosed) {
			if(logRegClosedEvents==null||(logRegClosedEvents!=null && !logRegClosedEvents.contains(event.getId()))) {
			transactionalScheduledService.maxRegistrations(event);
			logRegClosedEvents.add(event.getId());
			}
			
		}

	}
	
	
	
	
	@Scheduled(fixedDelay = 2000, initialDelay = 1000)
	public void eventStart() {

		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> eventsinProgress = allEvents.stream()
				.filter(event -> event.getStartTime().isBefore(mimicDateTime)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& !event.getStatus().equals(EEventStatus.EVENT_PROGRESS)
						&& !event.getStatus().equals(EEventStatus.CLOSED)
						&& !event.getStatus().equals(EEventStatus.CANCELLED))
				.collect(Collectors.toList());
		for (Event event : eventsinProgress) {
			if(logStartedEvents==null||(logStartedEvents!=null && !logStartedEvents.contains(event.getId()))) {

			transactionalScheduledService.eventStart(event);
			HashMap<String, String> params = new HashMap<>();
			params.put("[EVENT_NAME]", event.getTitle());
			for (EventParticipant participant : event.getParticipants()) {
				User user = userRepository.getById(participant.getId().getParticipantId());
				notificationMailService.sendNotificationEmail(user.getEmail(), "eventStart", params);
			}
			logStartedEvents.add(event.getId());
			}
			
		}

	}
	
	@Scheduled(fixedDelay = 2000, initialDelay = 2000)
	public void eventDone() {

		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> eventsClosed = allEvents.stream()
				.filter(event -> event.getEndTime().isBefore(mimicDateTime)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& !event.getStatus().equals(EEventStatus.CLOSED)
						&& !event.getStatus().equals(EEventStatus.CANCELLED)
						&& event.getStatus().equals(EEventStatus.EVENT_PROGRESS))
				.collect(Collectors.toList());
		for (Event event : eventsClosed) {
			if(logDoneEvents!=null && !logDoneEvents.contains(event.getId())) {

			transactionalScheduledService.eventDone(event);

			logDoneEvents.add(event.getId());
			}
		}

	}
	
	
	@Scheduled(fixedDelay = 2000, initialDelay = 3000)
	public void OpenParticipantForum() {

		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> eventsPastDeadline = allEvents.stream()
				.filter(event ->  event.getDeadline().isBefore(mimicDateTime)
						&& !event.getStatus().equals(EEventStatus.CLOSED)
						&& !event.getStatus().equals(EEventStatus.CANCELLED)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& !event.isPForumOpen())
				.collect(Collectors.toList());
		for (Event event : eventsPastDeadline) {
			if(logForumOpenEvents==null||(logForumOpenEvents!=null && !logForumOpenEvents.contains(event.getId()))) {

			transactionalScheduledService.OpenParticipantForum(event);
			logForumOpenEvents.add(event.getId());
			}
		}

	}
	

	@Scheduled(fixedDelay = 2000, initialDelay = 4000)
	public void closeParticipantForum() {

		List<Event> allEvents = eventRepository.findAll();
		
		LocalDateTime mimicDateTime = MimicClockTimeController.getMimicDateTime();
		List<Event> forumsToBeClosed = allEvents.stream()
				.filter(event ->  event.getEndTime().plusDays(3).isBefore(mimicDateTime)
						&& event.getStatus().equals(EEventStatus.CLOSED)
						&& event.getParticipants().size() >= event.getMinParticipants()
						&& event.isPForumOpen())
				.collect(Collectors.toList());
		System.out.println("forumsToBeClosed: "+forumsToBeClosed.size());
		System.out.println("logForumCloseEvents: "+logForumCloseEvents.size());
		for (Event event : forumsToBeClosed) {
			if(logForumCloseEvents==null||(logForumCloseEvents!=null && !logForumCloseEvents.contains(event.getId()))) {

			transactionalScheduledService.closeParticipantForum(event);
			logForumCloseEvents.add(event.getId());
			}
		}

	}

}
