package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipant;
import com.cmpe275.finalProject.cloudEventCenter.model.EventParticipentId;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipentId>{
	
	public List<EventParticipant>
	findById_EventIdAndId_ParticipantId(String eventId,String userId);
	
	public List<EventParticipant>
	findById_ParticipantId(String userId);
	
	public List<EventParticipant>
	findById_EventId(String eventId);
	
}
