package com.cmpe275.finalProject.cloudEventCenter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.EventData;

@Aspect
@Component
@EnableAspectJAutoProxy
@Order(0)
public class ValidationAspect {
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.EventService.addEvent(..)) && args(eventData)")
	public void eventValidationAdvice(JoinPoint joinPoint, EventData eventData) {
		
		System.out.printf("Permission check before the executuion of the method %s\n", joinPoint.getSignature().getName());
		
		//Validation should be updated
		if(eventData.getTitle().isBlank() || eventData.getDescription().isBlank())
			throw new IllegalArgumentException("Enter a valid title and desciption");
		
		if(eventData.getStartTime() == null || eventData.getEndTime() == null)
			throw new IllegalArgumentException("Enter a valid startTime/endTime");
		
		if(eventData.getEndTime().isBefore(eventData.getStartTime()) || eventData.getEndTime().isEqual(eventData.getStartTime())) {
			throw new IllegalArgumentException("Enter a startTime before endTime");
		}
			
		if(eventData.getStartTime().isBefore(eventData.getDeadline())) {
			throw new IllegalArgumentException("Enter a startTime after deadline");
		}
		
		if(eventData.getCity().isBlank() || eventData.getState().isBlank() || eventData.getZip().isBlank()) {
			throw new IllegalArgumentException("Enter a city, state and zipcode for the event");
		}
		
		if(eventData.getMinParticipants() < 0) {
			throw new IllegalArgumentException("Enter a positive minimum number of participants");
		}
		
		if(eventData.getMaxParticipants() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Enter a valid number of max participants");
		}
		
		//MUST BE RE-CHECKED - uncomment next few lines afte user controller
//		User eventOrganizerUser = eventData.getOrganizer();
//		List<Role> roles = eventOrganizerUser.getRoles();
//		if(eventData.getFee() > 0) {
//			boolean is_organization = false;
//			
//			for(Role r : roles) {
//				if(r.getRoleName() == "ORGANIZATION")
//					is_organization = true;
//			}
//			
//			if(!is_organization)
//				throw new IllegalArgumentException("Event organizer is not an ORGANIZATION, you cannot charge a fee");
//		}
	}
}
