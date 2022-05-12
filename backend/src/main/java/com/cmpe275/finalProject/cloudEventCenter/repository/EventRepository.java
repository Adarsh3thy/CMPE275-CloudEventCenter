package com.cmpe275.finalProject.cloudEventCenter.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cmpe275.finalProject.cloudEventCenter.model.Event;
import com.cmpe275.finalProject.cloudEventCenter.model.User;

public interface EventRepository extends JpaRepository<Event, String>{
	
	@Query("SELECT e FROM Event e WHERE e.organizer=?1")
	public Set<Event> findByOrganizerID(String organizerID);
	
	public List<Event> findByOrganizer(User organizer);
	
	public boolean existsByOrganizer(User organizer);
	
	@Query(value="select e.* from event e, participant_events p where e.event_id=p.event_id and p.user_id=?1", nativeQuery = true)
	public Set<Event> findEventsByUserId(String userId);
}