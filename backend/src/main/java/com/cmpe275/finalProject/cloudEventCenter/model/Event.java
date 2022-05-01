package com.cmpe275.finalProject.cloudEventCenter.model;


import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EVENT",catalog = "EVENT_CENTER")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","$$_hibernate_interceptor"})
@JsonInclude(Include.NON_NULL)
@Component
public class Event {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID")
	private long id;
	
	@Column(name = "EVENT_TITLE")
	private String title;
	
	@Column(name = "EVENT_DESC")
	private String description;
	
	 @Column(name = "EVENT_START_TIME")
	 private Timestamp startTime;
	 
	 @Column(name="EVENT_END_TIME")
	 private Timestamp  endTime;
	 
	 @Column(name="EVENT_DEADLINE")
	 private Timestamp  deadline;
	 
	 @Column(name="MIN_PARTICIPANTS")
	 private int minParticipants;
	 
	 @Column(name="MAX_PARTICIPANTS")
	 private int MaxParticipants;
	 
	 @Column(name="EVENT_FEE")
	 private double fee;
	 
	 @Column(name="ADMISSION_POLICY")
	 private String adminssionPolicy;
	
    @Embedded
    @AttributeOverrides(value = {
    		 @AttributeOverride(name = "street", column = @Column(name = "STREET")),
    		 @AttributeOverride(name = "city", column = @Column(name = "CITY")),
    		 @AttributeOverride(name = "state", column = @Column(name = "STATE")),
    		 @AttributeOverride(name = "zip", column = @Column(name = "ZIP"))
    })
	private Address address;
    
    
}
