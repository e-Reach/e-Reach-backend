package org.ereach.inc.services.validators;
import lombok.Getter;
import lombok.SneakyThrows;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.ereach.inc.utilities.Constants.CONSTRAINT_VIOLATION_TEMPLATE_MESSAGE;

@Service
@Getter
public class EmailValidator {

	private String[] domains;
	private String regExp;
	public void initialize() {
		domains = new String[]{
				"gmail.com", "outlook.com", "yahoo.com", "hotmail.com",
				"semicolon.africa.com", "hotmail.com", "hotmail.co.uk",
				"freenet.de"
		};
		regExp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		
	}
	
	@SneakyThrows
	public boolean isValid(String email) {
		if (!isValidEmail(email)) {
			String format = String.format(CONSTRAINT_VIOLATION_TEMPLATE_MESSAGE, Arrays.toString(domains));
			throw new RequestInvalidException(format);
		}
		return true;
	}
	
	public boolean isValidEmail(@NotNull String email){
		if (email.contains("@")){
			String[] emailSplit = email.split("@");
			Stream<String> filteredDomainStream = Arrays.stream(domains).filter(domain -> domain.equals(emailSplit[1]));
			return filteredDomainStream.findAny().isPresent();
		}
		return false;
	}
}
