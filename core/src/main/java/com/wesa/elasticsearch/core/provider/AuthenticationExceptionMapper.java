package com.wesa.elasticsearch.core.provider;

import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wesa.elasticsearch.core.model.RestError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper extends AbstractExceptionMapper<AuthenticationException> {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationExceptionMapper.class);

    /**
     * Adds error entity to response.
     */
    public Response toResponse(AuthenticationException exception) {
        logger.error(exception.getMessage(), exception);
        return buildResponseWithContentType(Response.status(Response.Status.UNAUTHORIZED).entity(
            new RestError(Response.Status.UNAUTHORIZED.getStatusCode(), exception.getMessage())));
    }

}
