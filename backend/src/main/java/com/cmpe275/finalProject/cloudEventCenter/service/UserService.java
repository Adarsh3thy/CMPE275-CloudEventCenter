package com.cmpe275.finalProject.cloudEventCenter.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe275.finalProject.cloudEventCenter.POJOs.JwtResponse;
import com.cmpe275.finalProject.cloudEventCenter.POJOs.MessageResponse;
import com.cmpe275.finalProject.cloudEventCenter.model.Address;
import com.cmpe275.finalProject.cloudEventCenter.model.ERole;
import com.cmpe275.finalProject.cloudEventCenter.model.RefreshToken;
import com.cmpe275.finalProject.cloudEventCenter.model.Role;
import com.cmpe275.finalProject.cloudEventCenter.model.User;
import com.cmpe275.finalProject.cloudEventCenter.repository.RoleRepository;
import com.cmpe275.finalProject.cloudEventCenter.repository.UserRepository;
import com.cmpe275.finalProject.cloudEventCenter.security.jwt.JwtUtils;

import net.bytebuddy.utility.RandomString;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	public ResponseEntity<?> createUser(String email, String password, String fullName, String screenName,
			String gender, String description, Set<String> strRoles, String number, String street, String city,
			String state, String zip, String siteURL)
			throws ParseException, MessagingException, UnsupportedEncodingException {

		if (userRepository.existsByEmail(email)) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		Address address = new Address(street, number, city, state, zip);

		User user = new User(null, email, fullName, screenName, passwordEncoder.encode(password), gender, description,
				null, true, null, address, null, null);

		Set<Role> roles = new HashSet<>();
		if (strRoles != null) {
			strRoles.forEach(role -> {
				switch (role) {
				case "organizer":

					Role adminRole = roleRepository.findByName(ERole.ROLE_ORGANIZER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "participant":
					Role userRole = roleRepository.findByName(ERole.ROLE_PARTICIPANT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		// user.setEnabled(false);
		user.setRoles(roles);
		userRepository.save(user);
		// sendVerificationEmail(user, siteURL);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

	}

	public ResponseEntity<?> login(String email, String password) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		System.out.println(userDetails.getId());
		System.out.println(userDetails.getUsername());
		System.out.println(userDetails.getEmail());

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		System.out.println("userDetails.getId(): "+userDetails.getId());
		 RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		System.out.println(roles);
		JwtResponse jwtResp = new JwtResponse(jwt,refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles);
		return ResponseEntity.ok(jwtResp);

	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {

		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		// props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.ssl.enable", "false");
		// props.put("mail.smtp.socketFactory.port", "587");
		// props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cmpe275Zhangproject@gmail.com", "Patr0nu$");
			}
		});
		session.setDebug(true);

		String toAddress = user.getEmail();
		String fromAddress = "cmpe275Zhangproject@gmail.com";
		String senderName = "Cloud Event Center";
		String subject = "Confirm Registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "VMS";

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(fromAddress, senderName));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
		message.setSubject(subject);

		String fName = user.getFullName() != null ? user.getFullName() : "";

		content = content.replace("[[name]]", fName + " ");
		String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		message.setContent("<h1>some content</h1>", "text/html");
		Transport transport = session.getTransport();
		transport.connect("smtp.gmail.com", "cmpe275Zhangproject@gmail.com", "Patr0nu$");
		transport.sendMessage(message, message.getAllRecipients());
		mailSender.send(message);

	}

	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);

			return true;
		}

	}
}
