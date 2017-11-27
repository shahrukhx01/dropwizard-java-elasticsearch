package com.wesa.elasticsearch.core.provider;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class AbstractExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

    protected Response buildResponseWithContentType(Response.ResponseBuilder builder) {
        return builder.type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
