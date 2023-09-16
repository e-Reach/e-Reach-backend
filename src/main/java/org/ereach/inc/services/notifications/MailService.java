package org.ereach.inc.services.notifications;

import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.http.ResponseEntity;

public interface MailService {
	
	ResponseEntity<EReachNotificationResponse> sendAccountActivationMail(EReachNotificationRequest notificationRequest) throws RequestInvalidException, RequestInvalidException;
	
}
