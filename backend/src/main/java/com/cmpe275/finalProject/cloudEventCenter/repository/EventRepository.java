package com.cmpe275.finalProject.cloudEventCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.cmpe275.finalProject.cloudEventCenter.model.Event;

public interface EventRepository extends JpaRepository<Event, String>{

}