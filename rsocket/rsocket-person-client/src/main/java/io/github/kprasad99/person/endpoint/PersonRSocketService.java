package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.client.PersonRSocketClient;
import io.github.kprasad99.person.endpoint.model.Person;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class PersonRSocketService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PersonRSocketClient client;

	@GetMapping("/person")
	public Flux<Person> list() {
	 	return client.list().map(e -> mapper.map(e, Person.class));
	}
}
