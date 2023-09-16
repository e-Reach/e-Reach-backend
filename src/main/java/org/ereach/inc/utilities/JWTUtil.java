package org.ereach.inc.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.ereach.inc.utilities.Constants.*;

public class JWTUtil {
	
	public static String generateAccountActivationToken(String email, String password, String phoneNumber){
		JWTCreator.Builder tokenCreator;
		if (email != null)
		    tokenCreator = buildTokenForEmails(email, password);
		else tokenCreator = buildTokenForTextMessages(phoneNumber, password);
		return tokenCreator.sign(Algorithm.HMAC512(password));
	}
	
	private static JWTCreator.Builder buildTokenForTextMessages(String phoneNumber, String password) {
		Map<String, String> claims = new HashMap<>();
		claims.put(USER_PASSWORD, password);
		claims.put(USER_PHONE_NUMBER, phoneNumber);
		return JWT.create()
				  .withClaim(CLAIMS, claims)
				  .withIssuer(LIBRARY_ISSUER_NAME)
				  .withExpiresAt(Instant.now().plusSeconds(7200))
				  .withIssuedAt(Instant.now());
	}
	
	private static JWTCreator.Builder buildTokenForEmails(String email, String password) {
		Map<String, String> claims = new HashMap<>();
		claims.put(USER_PASSWORD, password);
		claims.put(USER_MAIL, email);
		return JWT.create()
				  .withClaim(CLAIMS,claims)
				  .withExpiresAt(Instant.now().plusSeconds(3600))
				  .withIssuer(LIBRARY_ISSUER_NAME)
				  .withIssuedAt(Instant.now());
	}
	
	public static boolean isValidToken(String token, String notificationMedium, String secret) {
		if (Objects.equals(notificationMedium, MAIL)) {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret))
					                  .withIssuer(LIBRARY_ISSUER_NAME)
					                  .withClaimPresence(CLAIMS)
					                  .build();
			return verifier.verify(token)!=null;
		}
		if (Objects.equals(notificationMedium, TEXT_MESSAGE)) {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secret))
					                       .withIssuer(LIBRARY_ISSUER_NAME)
					                       .withClaimPresence(CLAIMS)
					                       .build();
			return verifier.verify(token)!=null;
		}
		else return false;
	}
	
	public static StringBuilder generateAccountActivationUrl(String email, String password, String phoneNumber){
		String GENERATED_TOKEN =  generateAccountActivationToken(email, password, phoneNumber);
		return new StringBuilder().append(BASE_URL).append(QUERY_STRING_PREFIX).append(QUERY_STRING_TOKEN).append(GENERATED_TOKEN);
	}
	
	public static String extractEmailFrom(String token) {
		Claim claim = JWT.decode(token).getClaim(CLAIMS);
		return claim.asMap().get(USER_MAIL).toString();
	}
	
	public static String extractPhoneNumberFrom(String token) {
		Claim claim = JWT.decode(token).getClaim(CLAIMS);
		return claim.asMap().get(USER_PHONE_NUMBER).toString();
	}
	
	public static String extractPasswordFromToken(String token){
		Claim claim = JWT.decode(token).getClaim(CLAIMS);
		return claim.asMap().get(USER_PASSWORD).toString();
	}
}
