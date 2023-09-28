package org.ereach.inc.services.validators;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.ereach.inc.data.models.annotations.PhoneNumber;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, Object> {
//	private final PhoneNumberUtil;
	@Override
	public void initialize(PhoneNumber constraintAnnotation) {
//		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
//		Phone phone = (Phone) value;
//		try {
//			if (phone.getRegionCode() != null)
//				return isValidForRegion(phone);
//			return isValid(phone);
//		} catch (RequestInvalidException | NumberParseException e) {
//			throw new RuntimeException(e);
//		}
		return true;
	}
	
	public boolean isValidForRegion(Object phone) throws NumberParseException {
//		Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parse(phone.getPhoneNumber(), phone.getRegionCode());
//		return phoneNumberUtil.isValidNumberForRegion(parsedNumber, phone.getRegionCode());
		return true;
	}
	
	public boolean isValid(Object phone) throws RequestInvalidException {
//		if (phone.getPhoneNumber().charAt(0) == '+'){
//			try {
//				Phonenumber.PhoneNumber parsedNumber = phoneNumberUtil.parse(phone.getPhoneNumber(), null);
//				return phoneNumberUtil.isValidNumber(parsedNumber);
//			} catch (Exception e) {
//				throw new RequestInvalidException(e.getMessage());
//			}
//		}
//		return false;
		return true;
	}
}
