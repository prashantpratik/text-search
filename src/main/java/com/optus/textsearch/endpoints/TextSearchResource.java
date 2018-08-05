package com.optus.textsearch.endpoints;

import com.optus.textsearch.models.SearchInput;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Controller
@Path("/counter-api")
/**
 * Resource interface to expost end points
 */
public interface TextSearchResource {
    /**
     * End point to search list of strings
     *
     * @param input
     * @return
     */
    @Path("/search")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    Response search(SearchInput input);

    /**
     * End point to search most occurred words
     *
     * @param id
     * @return
     */
    @Path("/top/{id}")
    @POST
    @Produces("text/csv")
    Response searchTop(@PathParam("id") int id);
}
