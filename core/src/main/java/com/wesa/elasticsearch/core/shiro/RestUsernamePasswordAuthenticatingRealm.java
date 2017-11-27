package com.wesa.elasticsearch.core.shiro;


import com.wesa.elasticsearch.core.model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthenticatingRealm;

public class RestUsernamePasswordAuthenticatingRealm extends AuthenticatingRealm {

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
      User user =null;
    return new BasicAuthenticationInfo(new BasicPrincipal(user), null, getName());
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof RestUsernamePasswordAuthenticationToken;
  }

}
