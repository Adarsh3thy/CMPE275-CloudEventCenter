package com.cmpe275.finalProject.cloudEventCenter.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cmpe275.finalProject.cloudEventCenter.enums.ForumTypes;
import com.cmpe275.finalProject.cloudEventCenter.model.SignUpForumQuestions;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "SignUpForumQuestionAnswers", catalog = "EVENT_CENTER")
public class SignUpForumQuestionsAnswers {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "FORUM_ID")
	private String id;

	// TODO: Foreign Key
	@Column(name = "EVENT_ID")
	private String event_id;
		
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
	private SignUpForumQuestions question_id;
	
	@Column(name = "label")
	private String text;
	
	// Assets
	// private List<MediaAsset<String>> assets
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime created_at;
	
    @UpdateTimestamp
	@Column(name = "MODIFIED_AT")
	private LocalDateTime modified_at;
	
	
}
