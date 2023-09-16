package org.ereach.inc.config;


import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;

@Configuration
@Getter
public class EReachConfig {
	
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
	
	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Bean
	public EReachConfig eReachConfig(){
		return new EReachConfig();
	}
	
}
