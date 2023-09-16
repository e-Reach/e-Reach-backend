package org.ereach.inc.services.notifications;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EReachNotificationRequest {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
}
