package com.cmpe275.finalProject.cloudEventCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe275.finalProject.cloudEventCenter.model.Event;

public interface EventInterface extends JpaRepository<Event, Long>{
}
