package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.ParticipantForum;

@Repository
public interface ParticipantForumRepository extends JpaRepository<ParticipantForum, String>{
	Boolean existsByEvent(Event event);
	
	Optional<ParticipantForum> findByEvent(Event event);
}
