package com.wesa.elasticsearch.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAuthenticationStrategy extends AtLeastOneSuccessfulStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationStrategy.class);

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        if (t != null) {
            if (t instanceof AuthenticationException) {
                // propagate as is
                logAuthenticationFailed(t);
                throw (AuthenticationException) t;
            }
        }
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }

    private void logAuthenticationFailed(Throwable t) {
        LOGGER.warn("Authentication failed ", t);
    }

}
