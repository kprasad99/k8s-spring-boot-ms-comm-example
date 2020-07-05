package io.github.kprasad99.person.client;

import io.github.kprasad99.person.endpoint.model.Person;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersonClient {

    private final WebClient client;

    public Flux<Person> persons() {
        return client.get().uri("/api/db/person").accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(Person.class);
    }

    public Mono<Person> findById(int id) {
        return client.get().uri("/api/db/person/" + id).accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(Person.class);
    }
}
