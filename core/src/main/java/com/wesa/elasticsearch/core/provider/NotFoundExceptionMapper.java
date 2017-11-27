package com.wesa.elasticsearch.core.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wesa.elasticsearch.core.model.RestError;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper extends AbstractExceptionMapper<NotFoundException> {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    /**
     * Adds error entity to response.
     */
    public Response toResponse(NotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildResponseWithContentType(Response.status(Response.Status.NOT_FOUND).entity(
            new RestError(Response.Status.NOT_FOUND.getStatusCode(), exception.getMessage())));
    }

}
