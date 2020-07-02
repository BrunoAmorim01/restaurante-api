package br.com.api.restaurante.config.keycloak;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Profile("keycloak")
@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class KeycloackConfig extends KeycloakWebSecurityConfigurerAdapter {
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());

		auth.authenticationProvider(keycloakAuthenticationProvider);

		Security.addProvider(new BouncyCastleProvider());
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {

		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}

	@Bean
	public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http
		.csrf().disable()		
		//.anonymous().disable()
		.cors()
		.and()
		.authorizeRequests().antMatchers("/arquivos/**").permitAll()
				// .antMatchers("/tokens**").permitAll()
				// .antMatchers(HttpMethod.GET,"/categorias*").hasAuthority(authority)
				// .antMatchers(HttpMethod.GET,"/categorias*").hasAnyRole("PESQUISAR_CATEGORIA")
				// .antMatchers(HttpMethod.GET,"/produtos*").hasRole("PESQUISAR_PRODUTO")

				// .hasRole("PESQUISAR_CATEGORIA")
				// .hasAuthority("SCOPE_read")
				.anyRequest().authenticated();

	}

	@Bean
	public FilterRegistrationBean<KeycloakAuthenticationProcessingFilter> keycloakAuthenticationProcessingFilterRegistrationBean(
			KeycloakAuthenticationProcessingFilter filter) {
		FilterRegistrationBean<KeycloakAuthenticationProcessingFilter> registrationBean = new FilterRegistrationBean<KeycloakAuthenticationProcessingFilter>(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<KeycloakPreAuthActionsFilter> keycloakPreAuthActionsFilterRegistrationBean(
			KeycloakPreAuthActionsFilter filter) {
		FilterRegistrationBean<KeycloakPreAuthActionsFilter> registrationBean = new FilterRegistrationBean<KeycloakPreAuthActionsFilter>(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<KeycloakAuthenticatedActionsFilter> keycloakAuthenticatedActionsFilterBean(
			KeycloakAuthenticatedActionsFilter filter) {
		FilterRegistrationBean<KeycloakAuthenticatedActionsFilter> registrationBean = new FilterRegistrationBean<KeycloakAuthenticatedActionsFilter>(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<KeycloakSecurityContextRequestFilter> keycloakSecurityContextRequestFilterBean(
			KeycloakSecurityContextRequestFilter filter) {
		FilterRegistrationBean<KeycloakSecurityContextRequestFilter> registrationBean = new FilterRegistrationBean<KeycloakSecurityContextRequestFilter>(
				filter);
		registrationBean.setEnabled(false);
		return registrationBean;
	}

	@Bean
	@Override
	@ConditionalOnMissingBean(HttpSessionManager.class)
	protected HttpSessionManager httpSessionManager() {
		return new HttpSessionManager();
	}

}
