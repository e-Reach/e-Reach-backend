package org.ereach.inc.utilities;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static org.ereach.inc.utilities.Constants.E_REACH_USERNAME_PREFIX;

public class UsernameGenerator {
	private final Set<String> usedUsernames = new HashSet<>();
	
	public String generateUniqueUsername(String patientFullName, String patientIdentificationNumber) {
		while (true) {
			String randomComponent = generateRandomComponent(patientFullName, patientIdentificationNumber);
			String uniquePart = hashUserInfo(patientFullName, randomComponent);
			String username = E_REACH_USERNAME_PREFIX + uniquePart;
			if (!usedUsernames.contains(username)) {
				usedUsernames.add(username);
				return username;
			}
		}
	}
	
	private String generateRandomComponent(String patientFullName, String patientIdentificationNumber) {
		SecureRandom random = new SecureRandom();
		String component = patientFullName + patientIdentificationNumber;
		int componentLength = component.length();
		StringBuilder randomComponent = new StringBuilder();
		for (int count = 0; count < componentLength; count++) {
			randomComponent.append(component.charAt(random.nextInt(componentLength)));
		}
		return randomComponent.toString();
	}
	
	private String hashUserInfo(String userInfo, String randomComponent) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest((userInfo + randomComponent).getBytes());
			return bytesToHex(hash).substring(ZERO.intValue(), TEN.intValue());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not available.");
		}
	}
	
	private String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte eachByte : bytes) {
			String hex = Integer.toHexString(0xFF & eachByte);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
