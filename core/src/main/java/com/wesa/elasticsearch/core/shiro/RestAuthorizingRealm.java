package com.wesa.elasticsearch.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;



/**
 */
public class RestAuthorizingRealm extends AuthorizingRealm {

  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }

  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    throw new UnsupportedOperationException("doGetAuthorizationInfo is not supported. Use isPermitted instead.");
  }

  public boolean isPermitted(PrincipalCollection principals, Permission permission) {
	  return true;
    
  }


  @Override
  public boolean hasRole(PrincipalCollection principal, String roleIdentifier) {

    return true;
  }

}
