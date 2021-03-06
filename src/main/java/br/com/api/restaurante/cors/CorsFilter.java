package br.com.api.restaurante.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.api.restaurante.RestauranteApiProperty;

//@Profile("keystone")
@Profile("oauth-security")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	@Autowired
	private RestauranteApiProperty apiProperty;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		res.setHeader("Access-Control-Allow-Origin", apiProperty.getOriginPermitida());
		res.setHeader("Access-Control-Allow-Credentials", "true");	
		
		
		if ("OPTIONS".equals(req.getMethod()) && apiProperty.getOriginPermitida().equals(req.getHeader("Origin"))) {
			
			res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			res.setHeader("Access-Control-Max-Age", "3600");
			res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			
			res.setStatus(HttpServletResponse.SC_OK);
		}else {			
			chain.doFilter(request, response);
		}

	}
	@Override
	public void destroy() {	
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}

}
