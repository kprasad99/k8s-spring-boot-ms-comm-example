package io.github.kprasad99.person.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.kprasad99.person.orm.dao.PersonDao;
import io.github.kprasad99.person.orm.model.Person;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/db/person")
@Slf4j
public class PersonRestService {

	@Autowired
	private PersonDao personDao;

	@GetMapping
	public List<Person> list(){
	    log.info("Listing all persons");
		return personDao.findAll();
	}

	@GetMapping("/{id}")
	public Person findById(@PathVariable("id")int id){
		log.info("Requesting data for person with id {}", id);
		return personDao.findById(id).orElse(null);
	}

}
