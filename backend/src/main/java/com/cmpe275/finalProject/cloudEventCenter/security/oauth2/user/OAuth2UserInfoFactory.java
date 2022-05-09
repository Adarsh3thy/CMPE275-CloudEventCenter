package com.cmpe275.finalProject.cloudEventCenter.security.oauth2.user;

import java.util.Map;

import com.cmpe275.finalProject.cloudEventCenter.exception.OAuth2AuthenticationProcessingException;
import com.cmpe275.finalProject.cloudEventCenter.model.AuthProvider;
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } 
      else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
