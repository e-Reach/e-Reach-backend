package org.ereach.inc.config;


import lombok.Getter;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.models.users.Practitioner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
@Getter
public class EReachConfig {
	
	@Getter
	@Value("${app.jwt.secret}")
	private String appJWTSecret;
	@Value("${mail.api.key}")
	private String mailApiKey;
	@Value("${cloud.api.key}")
	private String cloudApiKey;
	@Value("${tmsg.api.key}")
	private String textMessageApiKey;
	@Value("${cloud.api.name}")
	private String cloudApiName;
	@Value("${cloud.api.secret}")
	private String cloudApiSecret;
	@Value("${app.test.token}")
	private String testToken;
	
	@Bean
	public ModelMapper getModelMapper(){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
			  .setFieldMatchingEnabled(true)
			  .setFieldAccessLevel(PRIVATE);
		mapper.createTypeMap(CreatePractitionerRequest.class, Practitioner.class)
			.addMappings(mapping -> mapping.map(CreatePractitionerRequest::getRole, Practitioner::setUserRole));
		return mapper;
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
	
}
