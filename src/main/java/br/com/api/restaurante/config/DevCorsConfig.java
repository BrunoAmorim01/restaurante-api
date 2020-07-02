package br.com.api.restaurante.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.api.restaurante.RestauranteApiProperty;


@Configuration
public class DevCorsConfig implements WebMvcConfigurer {	
	
	@Autowired
	RestauranteApiProperty property;	
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
	
        registry.addMapping("/**")
        	.allowedOrigins(property.getOriginPermitida())
        	//.allowedOrigins("*")
        	//.allowCredentials(true)
        	.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        	.maxAge(3600)
        	.allowedHeaders("Authorization","Content-Type","Accept");
        	//.allowedHeaders("*");
        	
    }
	
	
}
