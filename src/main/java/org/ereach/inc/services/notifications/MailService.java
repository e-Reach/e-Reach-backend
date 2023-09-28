package org.ereach.inc.services.notifications;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.http.ResponseEntity;

public interface MailService {
	
	ResponseEntity<EReachNotificationResponse> sendAccountActivationMail(EReachNotificationRequest notificationRequest) throws RequestInvalidException, RequestInvalidException;

    ResponseEntity<EReachNotificationResponse> sendPatientInfo(EReachNotificationRequest request, String hospitalName) throws RequestInvalidException;
<<<<<<< HEAD
    ResponseEntity<EReachNotificationResponse> sendDoctorIdentificationNumber(EReachNotificationRequest request, String hospitalName) throws RequestInvalidException;
    ResponseEntity<EReachNotificationResponse> requestPatientAppointment(Appointments appointment) throws RequestInvalidException;
=======

	ResponseEntity<EReachNotificationResponse> sendMail(String email, String role, String firstName, String path, String lastName, String url) throws RequestInvalidException;
	ResponseEntity<EReachNotificationResponse> sendMail(EReachNotificationRequest notificationRequest) throws RequestInvalidException;
>>>>>>> f59229b90ed81e21c13dd32e429eb5c51639119d
}
