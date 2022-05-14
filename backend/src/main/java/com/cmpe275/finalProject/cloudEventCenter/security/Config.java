package com.cmpe275.finalProject.cloudEventCenter.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Config {
    @Value("${application.url.frontend:http://localhost:3000/}")
    private String frontEndURL;

    @Value("${application.url.backend:http://localhost:8080/}")
    private String backEndURL;
}
