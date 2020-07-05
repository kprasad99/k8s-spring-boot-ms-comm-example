package io.github.kprasad99.person.client;

import io.github.kprasad99.person.endpoint.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class PersonClient {

    private final RestTemplate restTemplate;

    @GetMapping
    public List<Person> list() {
        return Arrays.stream(restTemplate.getForObject("/api/db/person", Person[].class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") int id) {
        return restTemplate.getForObject("/api/db/person/" + id, Person.class);
    }

}
