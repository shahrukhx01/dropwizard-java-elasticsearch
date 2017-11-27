package com.wesa.elasticsearch.core.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 */
public class EmptyAuthenticationToken implements AuthenticationToken {


  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public Object getPrincipal() {
    return null;
  }

  public Object getCredentials() {
    return null;
  }
}
