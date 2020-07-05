package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.client.PersonClient;
import io.github.kprasad99.person.endpoint.model.Person;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/person")
@Slf4j
public class PersonRestService {

    @Autowired
    private PersonClient personClient;

    @GetMapping
    public Flux<Person> list() {
        log.info("Listing all persons");
        return personClient.persons();
    }

    @GetMapping("/{id}")
    public Mono<Person> findById(@PathVariable("id") int id) {
        log.info("Requesting data for person with id {}", id);
        return personClient.findById(id);
    }

}
