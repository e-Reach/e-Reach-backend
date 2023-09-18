package org.ereach.inc.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static org.ereach.inc.utilities.Constants.*;

public class PatientIdentificationNumberGenerator {
	private static final Set<String> usedPINs = new HashSet<>();
	
	public static String generateUniquePIN(String patientFullName, String patientPhoneNumber) {
		while (true) {
			String randomPIN = generateRandomPIN(patientFullName, patientPhoneNumber);
			String uniquePart = hashPatientInfo(patientFullName, randomPIN);
			String username = mapToLetter(uniquePart);
			System.out.println("username: "+username);
			if (!usedPINs.contains(uniquePart)) {
				usedPINs.add(uniquePart);
				return uniquePart;
			}
		}
	}
	
	private static String mapToLetter(String uniquePart) {
		StringBuilder mappedPart = new StringBuilder();
		for (int index = 0; index < uniquePart.length(); index++) {
			int numericValue = Character.getNumericValue(uniquePart.charAt(index));
			mappedPart.append(numericValue);
			mappedPart.append(uniquePart.charAt(index));
		}
		return mappedPart.toString().toUpperCase();
	}
	
	private static String generateRandomPIN(String patientFullName, String phoneNumber) {
		SecureRandom random = new SecureRandom();
		String phoneNumberSubString = phoneNumber.substring(3, 9);
		String component = patientFullName + phoneNumber;
		StringBuilder randomPIN = new StringBuilder();
		for (int count = 0; count < component.length(); count++) {
			randomPIN.append(component.charAt(random.nextInt(component.length())));
		}
		return randomPIN + phoneNumberSubString;
	}
	
	private static String hashPatientInfo(String patientInfo, String randomPIN) {
		try {
			MessageDigest digest = MessageDigest.getInstance(SHA_256_ALGORITHM);
			byte[] hash = digest.digest((patientInfo + randomPIN).getBytes());
			return bytesToHex(hash).substring(ZERO.intValue(), TEN.intValue());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(SHA_256_ALGORITHM_NOT_AVAILABLE);
		}
	}
	
	private static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
