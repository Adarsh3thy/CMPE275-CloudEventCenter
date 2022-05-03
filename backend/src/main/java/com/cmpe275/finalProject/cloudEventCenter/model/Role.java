package com.cmpe275.finalProject.cloudEventCenter.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ROLE")
public class Role{
	@Id
	@Column(name = "ID_")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "AUTHORITY_")
	private String roleName;

	
}
