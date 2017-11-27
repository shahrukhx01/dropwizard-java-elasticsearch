package com.wesa.elasticsearch.core.provider;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wesa.elasticsearch.core.model.RestError;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper extends AbstractExceptionMapper<BadRequestException> {
    private static final Logger logger = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

    /**
     * Adds error entity to response.
     */
    public Response toResponse(BadRequestException exception) {
        logger.error(exception.getMessage(), exception);
        return buildResponseWithContentType(Response.status(Response.Status.BAD_REQUEST).entity(
            new RestError(Response.Status.BAD_REQUEST.getStatusCode(), exception.getMessage())));
    }

}
