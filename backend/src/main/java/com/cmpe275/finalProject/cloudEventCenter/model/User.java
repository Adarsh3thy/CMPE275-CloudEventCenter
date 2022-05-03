package com.cmpe275.finalProject.cloudEventCenter.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name = "USER",catalog = "EVENT_CENTER")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "EMAIL",unique = true)
    private String email;
    
    @Column(name = "FULL_NAME")
    private String fullName;
    
    @Column(name = "SCREEN_NAME")
    private String screenName;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "GENDER")
    private String gender;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "VERIFICATION_CODE")
    private String verificationCode;
    
    @Column(name = "ENABLED")
    private boolean enabled;
    
    @Column(name = "PASSCODE")
    private String passcode;
    
    @Embedded
    private Address address;

    @ManyToMany(cascade = CascadeType.MERGE)
	 @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	 private List<Role> roles;

   
}
