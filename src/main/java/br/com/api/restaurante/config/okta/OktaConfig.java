package br.com.api.restaurante.config.okta;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("okta")
@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OktaConfig extends WebSecurityConfigurerAdapter {
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http		
		.cors()
		.and()
		.authorizeRequests()		
		//.antMatchers("/").permitAll()
		.antMatchers("/arquivos/**").permitAll()
		.anyRequest().authenticated()				
		.and()
		.oauth2Login()		
		//.and()
		//.oauth2Client()
		.and()
		.oauth2ResourceServer().jwt();		
	}	
}
