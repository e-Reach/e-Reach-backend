package org.ereach.inc.services.notifications;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.http.ResponseEntity;

public interface MailService {
	
	ResponseEntity<EReachNotificationResponse> sendAccountActivationMail(EReachNotificationRequest notificationRequest) throws RequestInvalidException, RequestInvalidException;

    ResponseEntity<EReachNotificationResponse> sendPatientInfo(EReachNotificationRequest request, String hospitalName) throws RequestInvalidException;

	ResponseEntity<EReachNotificationResponse> sendMail(String email, String role, String name, String path) throws RequestInvalidException;
}
