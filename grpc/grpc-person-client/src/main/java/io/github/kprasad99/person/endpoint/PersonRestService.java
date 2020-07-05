package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.client.PersonClient;
import io.github.kprasad99.person.endpoint.model.Person;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
@Slf4j
public class PersonRestService {

    private final PersonClient client;
    private ModelMapper modelMapper;

    public PersonRestService(PersonClient client, ModelMapper modelMapper) {
        super();
        this.client = client;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<Person> list() {
        log.info("Listing all persons");
        return client.list().stream().map(toModel).collect(Collectors.toList());
    }

    public Function<io.github.kprasad99.person.proto.PersonProto.Person, io.github.kprasad99.person.endpoint.model.Person> toModel = p -> modelMapper
            .map(p, Person.class);

}
