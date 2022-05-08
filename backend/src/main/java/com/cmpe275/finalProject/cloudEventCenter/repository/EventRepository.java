package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;

public interface EventRepository extends JpaRepository<Event, String>{
	
	@Query("SELECT e FROM Event e WHERE e.Organizer=?1")
	public List<Event> findByOrganizerID(String organizerID);
}