package com.wesa.elasticsearch.core.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 */
public class RestUsernamePasswordAuthenticationToken implements AuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public RestUsernamePasswordAuthenticationToken(String username, String password) {
		this.username = username;
		this.password = password;
		;
	}

	public Object getPrincipal() {
		return username;
	}

	public Object getCredentials() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
