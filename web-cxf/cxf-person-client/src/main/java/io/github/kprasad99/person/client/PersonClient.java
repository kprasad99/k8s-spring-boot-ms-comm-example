package io.github.kprasad99.person.client;

import io.github.kprasad99.person.endpoint.model.Person;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/db/person")
public interface PersonClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> list();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person findById(@PathParam("id")int id);

}
