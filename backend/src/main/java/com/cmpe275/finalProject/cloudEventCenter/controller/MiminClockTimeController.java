package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.model.MimicClockTime;

@RestController
public class MiminClockTimeController {
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/clocktime", 
			method = RequestMethod.GET
	)
	String getTime(){
		ZoneId zoneSingapore = ZoneId.of("America/Los_Angeles");  
		return MimicClockTime.getCurrentTime().instant().atZone(zoneSingapore).toString();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(
			value = "/clocktime/{date}", 
			method = RequestMethod.POST
	)
	ResponseEntity<?> setTime(@PathVariable("date") String date) throws ParseException{
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		 Date inputDate = sdformat.parse(date);
		 String currentDate = sdformat.format(new Date());
		 Date curDate=sdformat.parse(currentDate);
		 Calendar c = Calendar.getInstance();
		 c.setTime(curDate);
		 c.add(Calendar.YEAR, 1);
		 Date currentDatePlusOne = sdformat.parse(
				 sdformat.format(
				 c.getTime()));
		// System.out.println("currentDatePlusOne :"+currentDatePlusOne);
		 if(inputDate.compareTo(curDate)>=0
			&& 	 inputDate.compareTo(currentDatePlusOne)<=0
				 )
			 MimicClockTime.getInstance(date);
		 else
			 return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body("Invalid Request");
		
		 return ResponseEntity
		            .status(HttpStatus.OK)
		            .body("Success");
	}
}
