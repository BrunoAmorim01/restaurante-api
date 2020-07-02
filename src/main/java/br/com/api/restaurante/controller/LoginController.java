package br.com.api.restaurante.controller;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
//@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@RequestMapping
	public boolean login(@RequestBody User user) {
		return user.getUsername().equals("admin@restaurante.com");
	}
	
	@RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
          .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
          .decode(authToken)).split(":")[0];
    }
	
	@GetMapping(path = "/basicauth")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }
	
	public static class AuthenticationBean{
		 private String message;

		    public AuthenticationBean(String message) {
		        this.message = message;
		    }

		    public String getMessage() {
		        return message;
		    }

		    public void setMessage(String message) {
		        this.message = message;
		    }

		    @Override
		    public String toString() {
		        return String.format("HelloWorldBean [message=%s]", message);
		    }
	}
}
