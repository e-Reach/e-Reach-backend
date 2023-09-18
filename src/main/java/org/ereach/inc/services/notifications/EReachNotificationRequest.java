package org.ereach.inc.services.notifications;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EReachNotificationRequest {
	
	private String fullName;
	private String username;
	private String email;
	private String password;
	private String phoneNumber;
}
