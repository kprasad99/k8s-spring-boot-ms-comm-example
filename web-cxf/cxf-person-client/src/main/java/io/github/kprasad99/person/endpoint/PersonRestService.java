package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.client.PersonClient;
import io.github.kprasad99.person.endpoint.model.Person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
@Slf4j
@AllArgsConstructor
public class PersonRestService {

    private final PersonClient personClient;

    @GetMapping
    public List<Person> list() {
        log.info("Listing all persons");
        return personClient.list();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") int id) {
        log.info("Requesting data for person with id {}", id);
        return personClient.findById(id);
    }

}
