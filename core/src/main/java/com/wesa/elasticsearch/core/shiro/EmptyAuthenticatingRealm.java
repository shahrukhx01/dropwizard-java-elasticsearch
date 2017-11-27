package com.wesa.elasticsearch.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wesa.elasticsearch.core.model.User;

public class EmptyAuthenticatingRealm extends AuthenticatingRealm {

    private static final Logger logger = LoggerFactory.getLogger(EmptyAuthenticatingRealm.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.debug("Empty authentication done.");
        User user = new User();
        return new BasicAuthenticationInfo(new BasicPrincipal(user), null, getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof EmptyAuthenticationToken;
    }

}
