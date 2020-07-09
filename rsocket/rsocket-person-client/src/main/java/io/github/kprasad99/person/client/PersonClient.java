package io.github.kprasad99.person.client;


import io.github.kprasad99.person.proto.PersonProto.Person;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class PersonClient {

    private final WebClient client;

    public Flux<Person> persons() {
        return client.get().uri("/api/proto/db/person").accept(MediaType.parseMediaType("application/x-protobuf")).retrieve().bodyToFlux(Person.class);
    }
}
