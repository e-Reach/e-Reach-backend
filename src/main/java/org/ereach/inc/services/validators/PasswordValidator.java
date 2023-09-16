package org.ereach.inc.services.validators;

import lombok.Getter;
import lombok.SneakyThrows;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Getter
public class PasswordValidator{
	
	private String regExpression;

	public void initialize() {
		regExpression = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$&.!*?]).{8,12}$";
	}
	
	@SneakyThrows
	public boolean isValidPassword(String password) {
		Pattern compiledPattern = Pattern.compile(regExpression);
		if (!compiledPattern.matcher(password).matches()) {
			FieldInvalidException fieldInvalidException = new FieldInvalidException("Invalid Password");
			fieldInvalidException.setExceptionCause("Password does not obey stated contract for password");
			fieldInvalidException.setReasons(new String[]{
					"Password must be between 8-12 characters long",
					"Password must contain at least one uppercase letter and one digit",
					"Password must contain at least one of the special characters: @, #, $, &, ., !, *,"
			});
			throw fieldInvalidException;
		}
		return true;
	}
	
	public boolean isValid(String password) {
		return isValidPassword(password);
	}
}
