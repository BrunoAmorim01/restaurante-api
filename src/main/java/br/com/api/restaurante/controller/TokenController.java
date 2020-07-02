package br.com.api.restaurante.controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.restaurante.RestauranteApiProperty;

//@Profile("oauth-security")
@RestController
@RequestMapping("/tokens")

public class TokenController {

	@Autowired
	private RestauranteApiProperty restauranteApiProperty;

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(restauranteApiProperty.getSeguranca().isEnableHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);

		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());

	}

	// @ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping("/encerrar-sessao")
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// public void logout() {
		System.out.println("logout");
		System.out.println("getContextPath: " + req.getContextPath());
		System.out.println("getLocalName: " + req.getLocalName());
		System.out.println("getRequestURL: " + req.getRequestURL().toString());
		System.out.println("getSession().getServletContext().getServletContextName(): "
				+ req.getSession().getServletContext().getServletContextName());

		System.out.println("getUserPrincipal: " + req.getUserPrincipal());
		System.out.println("getAuthType: " + req.getAuthType());
		System.out.println("getMethod: " + req.getMethod());
		//System.out.println("getAttribute: " + req.getAttribute(KeycloakSecurityContext.class.getName()));
		
		req.logout();

		resp.setStatus(HttpStatus.NO_CONTENT.value());

	}

}
