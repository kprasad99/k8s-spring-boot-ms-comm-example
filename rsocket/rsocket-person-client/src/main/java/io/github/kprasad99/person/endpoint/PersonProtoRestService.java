package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.client.PersonClient;
import io.github.kprasad99.person.endpoint.model.Person;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/proto/person")
public class PersonProtoRestService {

    @Autowired
    private PersonClient personClient;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public Flux<Person> getProto() {
        return personClient.persons().map(e -> mapper.map(e, Person.class)).subscribeOn(Schedulers.elastic());
    }

}
