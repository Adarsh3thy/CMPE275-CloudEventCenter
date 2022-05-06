package com.cmpe275.finalProject.cloudEventCenter.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.finalProject.cloudEventCenter.config.Config;
import com.cmpe275.finalProject.cloudEventCenter.payload.request.LoginRequest;
import com.cmpe275.finalProject.cloudEventCenter.payload.request.SignupRequest;
import com.cmpe275.finalProject.cloudEventCenter.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	Config urlConfig;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request)
			throws ParseException, MessagingException, UnsupportedEncodingException

	{

		return userService.createUser(signUpRequest.getEmail(), signUpRequest.getPassword(),
				signUpRequest.getFullName(), signUpRequest.getScreenName(), signUpRequest.getGender(),
				signUpRequest.getDescription(), signUpRequest.getRole(), signUpRequest.getNumber(),
				signUpRequest.getStreet(), signUpRequest.getCity(), signUpRequest.getState(), signUpRequest.getZip(),
				getSiteURL(request));
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		System.out.println(siteURL.replace(request.getServletPath(), ""));
		return siteURL.replace(request.getServletPath(), "");
	}

	@GetMapping("/verify")
	public String verifyUser(@RequestParam("code") String code) {
		if (userService.verify(code)) {
			return "<html>\n" + "    <body>\n" + "        <p>Verification Successful.</p>\n" + "<a href="
					+ urlConfig.getFrontEndURL() + " target='_blank'>Login</a>" + "    </body>\n" + "</html>\n";
		} else {
			return "<html>\n" + "    <body>\n" + "        <p>Verification Failed</p>\n" + "    </body>\n" + "</html>\n";
		}
	}
}