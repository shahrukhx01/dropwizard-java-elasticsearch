package com.wesa.elasticsearch.core.provider;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wesa.elasticsearch.core.model.RestError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorizationExceptionMapper extends AbstractExceptionMapper<AuthorizationException> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationExceptionMapper.class);

    /**
     * Adds error entity to response.
     */
    public Response toResponse(AuthorizationException exception) {
        logger.error(exception.getMessage(), exception);

        return buildResponseWithContentType(Response.status(Response.Status.FORBIDDEN).entity(
            new RestError(Response.Status.FORBIDDEN.getStatusCode(), "Authorization failed")));
    }

}
