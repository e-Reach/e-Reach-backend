package org.ereach.inc.services.notifications;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.utilities.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.TemplateLoader.loadTemplateContent;

@Service
@AllArgsConstructor
@Slf4j
public class EReachMailer implements MailService{
	
	private final ResourceLoader resourceLoader;
	private final RestTemplate restTemplate;
	private final TemplateEngine templateEngine;
	private ModelMapper modelMapper;
	private EReachConfig eReachConfig;


	@Override
	public ResponseEntity<EReachNotificationResponse> sendAccountActivationMail(EReachNotificationRequest notificationRequest)throws RequestInvalidException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(API_KEY, eReachConfig.getMailApiKey());
		headers.setContentType(MediaType.APPLICATION_JSON);

		Resource foundTemplateResource = resourceLoader.getResource(ACTIVATION_MAIL_PATH);
		String templateContent = loadTemplateContent(foundTemplateResource);

		Recipient recipient = modelMapper.map(notificationRequest, Recipient.class);
		Notification notification = new Notification();
		notification.setRecipients(Collections.singletonList(recipient));
		notification.setSender(Sender.builder()
									 .name(SENDER_FULL_NAME)
									 .email(SENDER_EMAIL)
									 .build());
		notification.setSubject(MESSAGE_SUBJECT);
		notification.setHtmlContent(templateContent);
		

		HttpEntity<Notification> requestEntity = new HttpEntity<>(notification, headers);
		ResponseEntity<EReachNotificationResponse> response = restTemplate.postForEntity(
				BREVO_SEND_EMAIL_API_URL,
				requestEntity, EReachNotificationResponse.class
		);
		if (response.getStatusCode().is2xxSuccessful())
			log.info("{} response body:: {}", MESSAGE_SUCCESSFULLY_SENT, Objects.requireNonNull(response.getBody()));
		else log.error("{} response body:: {}", MESSAGE_FAILED_TO_SEND, Objects.requireNonNull(response.getBody()));
		return response;
	}

	@Override
	public ResponseEntity<EReachNotificationResponse> sendPatientInfo(EReachNotificationRequest request, String hospitalName) throws RequestInvalidException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(API_KEY, eReachConfig.getMailApiKey());
		headers.setContentType(MediaType.APPLICATION_JSON);

		Resource foundTemplateResource = resourceLoader.getResource(PATIENT_ID_MAIL_PATH);
		String templateContent = loadTemplateContent(foundTemplateResource);

		String formattedContent = String.format(templateContent, request.getFullName(), hospitalName, LocalDate.now(), request.getUsername(), request.getPassword());
		Recipient recipient = modelMapper.map(request, Recipient.class);

		Notification notification = new Notification();
		notification.setRecipients(Collections.singletonList(recipient));
		notification.setSender(Sender.builder()
				.name(SENDER_FULL_NAME)
				.email(SENDER_EMAIL)
				.build());
		notification.setSubject(MESSAGE_SUBJECT);
		notification.setHtmlContent(formattedContent);
		HttpEntity<Notification> requestEntity = new HttpEntity<>(notification, headers);
		ResponseEntity<EReachNotificationResponse> response = restTemplate.postForEntity(
				BREVO_SEND_EMAIL_API_URL,
				requestEntity, EReachNotificationResponse.class
		);
		if (response.getStatusCode().is2xxSuccessful())
			log.info("{} response body:: {}", MESSAGE_SUCCESSFULLY_SENT, Objects.requireNonNull(response.getBody()));
		else log.error("{} response body:: {}", MESSAGE_FAILED_TO_SEND, Objects.requireNonNull(response.getBody()));
		return response;
	}
	
	@Override
	public Object sendMail(String email, String username, String name, String path) throws RequestInvalidException {
		HttpHeaders headers = new HttpHeaders();
		headers.set(API_KEY, eReachConfig.getMailApiKey());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Resource foundTemplateResource = resourceLoader.getResource(path);
		String templateContent = loadTemplateContent(foundTemplateResource);
		String formattedContent = String.format(templateContent,name, url(email, username));
		
		Recipient recipient = new Recipient();
		recipient.setEmail(email);
		recipient.setFullName(name);
		recipient.setUsername(username);
		
		Notification notification = new Notification();
		notification.setRecipients(Collections.singletonList(recipient));
		notification.setSender(Sender.builder()
				                       .name(SENDER_FULL_NAME)
				                       .email(SENDER_EMAIL)
				                       .build());
		notification.setSubject(MESSAGE_SUBJECT);
		notification.setHtmlContent(formattedContent);
		HttpEntity<Notification> requestEntity = new HttpEntity<>(notification, headers);
		ResponseEntity<EReachNotificationResponse> response = restTemplate.postForEntity(
				BREVO_SEND_EMAIL_API_URL,
				requestEntity, EReachNotificationResponse.class
		);
		if (response.getStatusCode().is2xxSuccessful())
			log.info("{} response body:: {}", MESSAGE_SUCCESSFULLY_SENT, Objects.requireNonNull(response.getBody()));
		else log.error("{} response body:: {}", MESSAGE_FAILED_TO_SEND, Objects.requireNonNull(response.getBody()));
		return response;
	}
	
	private String url(String email, String name) {
		String frontendComponentUrl = "";
		return FRONTEND_BASE_URL + frontendComponentUrl + JWTUtil.generateToken(email, name, null);
	}
	
}
