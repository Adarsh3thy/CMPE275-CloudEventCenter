package com.cmpe275.finalProject.cloudEventCenter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.joda.time.LocalDateTime;

@Aspect
public class ValidationAspect {
	
	@Before("execution(public * com.cmpe275.finalProject.cloudEventCenter.service.EventService.addEvent(..)) "
			+ "&& args(title, description, startTime, endTime, deadline, street, number, city, state, zip, \r\n"
			+ "	    		minParticipants, maxParticipants, fee, admissionPolicy)")
	public void eventValidationAdvice(JoinPoint joinPoint, String title, String description, LocalDateTime startTime, 
			LocalDateTime endTime, LocalDateTime deadline, String street, String number, String city, String state,
			String zip, int minParticipants, int maxParticipants, double fee, String admissionPolicy) {
		
		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		
		//Validation should be updated
		if(title.isBlank() || description.isBlank())
			throw new IllegalArgumentException("Enter a valid title and desciption");
			
	}
}
