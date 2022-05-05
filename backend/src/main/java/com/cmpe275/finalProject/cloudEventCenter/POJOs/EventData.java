package com.cmpe275.finalProject.cloudEventCenter.POJOs;

import java.time.LocalDateTime;

import com.cmpe275.finalProject.cloudEventCenter.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventData {
	String title;
	String description;
//	String startTime; 
//	String endTime;
//	String deadline; 
	LocalDateTime startTime; 
	LocalDateTime endTime;
	LocalDateTime deadline; 
	String street;
	String number; 
	String city; 
	String state;
	String zip; 
	int minParticipants;
	int maxParticipants; 
	double fee;
	boolean approvalReq;
	User organizer;
}
