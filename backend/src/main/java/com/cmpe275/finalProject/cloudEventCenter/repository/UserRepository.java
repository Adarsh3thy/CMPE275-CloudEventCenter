package com.cmpe275.finalProject.cloudEventCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cmpe275.finalProject.cloudEventCenter.model.User;

public interface UserRepository extends JpaRepository<User, String>{

}
