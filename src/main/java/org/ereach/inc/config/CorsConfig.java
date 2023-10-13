package org.ereach.inc.config;

import jakarta.servlet.FilterRegistration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;
import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        corsConfig.addAllowedMethod("*");
        corsConfig.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name()));
        corsConfig.setAllowedOrigins(Arrays.asList(
                "https://e-reach-prod.up.railway.app/",
                "http://localhost:3000/",
                "https://e-reach.vercel.app/"));
        corsConfig.addAllowedOrigin("https://e-reach-prod.up.railway.app/");
        corsConfig.addAllowedOrigin("http://localhost:3000/");
        corsConfig.addAllowedOrigin("https://e-reach.vercel.app/");

        source.registerCorsConfiguration("/**", corsConfig);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }
}
