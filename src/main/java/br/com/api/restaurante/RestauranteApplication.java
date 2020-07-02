package br.com.api.restaurante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(RestauranteApiProperty.class)
public class RestauranteApplication {

	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		//APPLICATION_CONTEXT = SpringApplication.run(RestauranteApiProperty.class, args);
		 SpringApplication.run(RestauranteApplication.class, args);
	}

	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}

}
