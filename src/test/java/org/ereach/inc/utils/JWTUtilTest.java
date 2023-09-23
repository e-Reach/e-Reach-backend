package org.ereach.inc.utils;

import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JWTUtilTest {
	
	@Test
	void testThatTokenIsGeneratedForEmail(){
		String token = JWTUtil.generateActivationToken("alaabdulmalik03@gmail.com", "ayanniyi@20", null);
		System.out.println("token:: "+token);
		assertThat(token).isNotNull();
	}
	
	@Test void testThatTokenIsGeneratedForPhoneNumbers(){
		String token = JWTUtil.generateActivationToken(null, "ayanniyi@20", "+2347036174617");
		System.out.println("token:: "+token);
		assertThat(token).isNotNull();
	}
	
	@Test void testThatAccountActivationTokenIsContainsBaseUrl(){
		String token = JWTUtil.generateAccountActivationUrl(null, "ayanniyi@20", "+2347036174617").toString();
		System.out.println("token:: "+token);
		assertThat(token).contains("https://localhost:3000/library_management");
	}
	
	@Test void generatedTokenCanBeDecodedAsAValidJWTToken(){
		String password = "ayanniyi@20";
		String phoneNumber = "+2347036174617";
		String token = JWTUtil.generateActivationToken(null, password, phoneNumber);
		assertThat(JWTUtil.isValidToken(token, "text message")).isTrue();
	}
	
	@Test void testThatEmailCanBeExtractedFromToken(){
		String password = "ayanniyi@20";
		String phoneNumber = "+2347036174617";
		String email = "alaabdulmalik03@gmail.com";
		String token = JWTUtil.generateActivationToken(email, password, null);
		String extractedEmail = JWTUtil.extractEmailFrom(token);
		assertThat(extractedEmail).isEqualTo(email);
	}
	
	@Test void testThatPhoneNumberCanBeExtractedFromToken(){
		String password = "ayanniyi@20";
		String phoneNumber = "+2347036174617";
		String token = JWTUtil.generateActivationToken(null, password, phoneNumber);
		String extractedPhoneNumber = JWTUtil.extractPhoneNumberFrom(token);
		assertThat(extractedPhoneNumber).isEqualTo(phoneNumber);
	}
	
	@Test void testThatFullPayloadCanBeExtractedFromToken(){
		String password = "ayanniyi@20";
		String phoneNumber = "+2347036174617";
		String token = JWTUtil.generateActivationToken(null, password, phoneNumber);
		String extractedPassword = JWTUtil.extractRoleFromToken(token);
		assertThat(extractedPassword).isEqualTo(password);
	}
}
