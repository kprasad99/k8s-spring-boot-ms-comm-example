package io.github.kprasad99.person.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.kprasad99.person.orm.dao.PersonDao;
import io.github.kprasad99.person.orm.model.Person;
import lombok.extern.slf4j.Slf4j;

@Service
@Path("/api/db/person")
@Slf4j
public class PersonRestService {

	@Autowired
	private PersonDao personDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> list(){
	    log.info("Listing all persons");
		return personDao.findAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person findById(@PathParam("id")int id){
		log.info("Requesting data for person with id {}", id);
		return personDao.findById(id).orElse(null);
	}

}
