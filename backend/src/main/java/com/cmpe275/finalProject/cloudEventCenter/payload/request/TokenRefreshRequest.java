package com.cmpe275.finalProject.cloudEventCenter.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class TokenRefreshRequest {
	  @NotBlank
	  private String refreshToken;
	 
	}
