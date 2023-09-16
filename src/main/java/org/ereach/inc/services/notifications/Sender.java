package org.ereach.inc.services.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Sender {
	
	@JsonProperty("name")
	private String name;
	@JsonProperty("email")
	private String email;
}
