package com.wesa.elasticsearch.core.shiro;


import org.apache.shiro.authc.SimpleAuthenticationInfo;

public class BasicAuthenticationInfo extends SimpleAuthenticationInfo {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicAuthenticationInfo(Object principal, Object credentials, String realmName) {
        super(principal, credentials, realmName);
    }
}
