package me.sasidhar;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Created by Sasidhar on 4/4/15.
 */
@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Context
    private HttpHeaders headers;

    public Response toResponse(NotFoundException ex){
        return Response.status(404)
                .entity("{\"error\" : \"requested resource not found\"}")
                .type("application/json")
                .build();
    }

}
