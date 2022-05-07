package com.cmpe275.finalProject.cloudEventCenter.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "PARTICIPANT_FORUM", catalog = "EVENT_CENTER")
public class ParticipantForum {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PTS_FORUM_ID")
	private String id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "EVENT_ID", referencedColumnName = "EVENT_ID")
	private Event event;

	@Column(name = "IS_READ_ONLY")
	boolean isReadOnly;

	// need to add questions
	
	@Column(name = "STATUS")
	private String status;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "pts_forum_users", joinColumns = @JoinColumn(name = "PTS_FORUM_ID"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "$$_hibernate_interceptor", "Organizer",
			"participants" })
	private Set<User> users;
	
	
	
}
