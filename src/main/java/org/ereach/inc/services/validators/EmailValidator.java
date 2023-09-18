package org.ereach.inc.services.validators;
import lombok.Getter;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.ereach.inc.utilities.Constants.CONSTRAINT_VIOLATION_MESSAGE;

@Service
@Getter
public class EmailValidator {

	private final String[] domains = new String[]{
			"gmail.com", "outlook.com", "yahoo.com", "hotmail.com",
			"semicolon.africa.com", "hotmail.co.uk", "freenet.de"
	};
	private final String regExp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	

	public void validateEmail(String email) throws RequestInvalidException {
		if (!isValidDomain(email) || !emailMatchesPattern(email)) {
			String format = String.format(CONSTRAINT_VIOLATION_MESSAGE, Arrays.toString(domains));
			throw new RequestInvalidException(format);
		}
	}

	private boolean emailMatchesPattern(String email) {
		Pattern compiledPattern = Pattern.compile(regExp);
        return compiledPattern.matcher(email).matches();
    }

	private boolean isValidDomain(@NotNull String email){
		if (email.contains("@")){
			String[] emailSplit = email.split("@");
			Stream<String> filteredDomainStream = Arrays.stream(domains).filter(domain -> domain.equals(emailSplit[1]));
			return filteredDomainStream.findAny().isPresent();
		}
		return false;
	}
}
