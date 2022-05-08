package com.cmpe275.finalProject.cloudEventCenter.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "SignUpForumQuestions", catalog = "EVENT_CENTER")
public class SignUpForumQuestions {
	
	/**
	 * question_id
	 * created_by
	 * text
	 * assets
	 * created_at
	 * updated_at
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "QUESTION_ID")
	private String id;
	
	// TBD: Foreign Key
	@Column(name = "CREATED_BY")
	private String user_id;
	
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
	@Column(name = "EVENT_ID")
//	private Event event;
	private String event_id;
	
//	@Column(name = "ASSETS")
//	private List<String> assets;
	@Column(name = "TEXT")
	private String text;
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime created_at;
	
    @UpdateTimestamp
	@Column(name = "MODIFIED_AT")
	private LocalDateTime modified_at;
	
	
}
