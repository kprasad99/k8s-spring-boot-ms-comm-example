package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.orm.dao.PersonDao;
import io.github.kprasad99.person.proto.PersonProto.Person;

import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/proto/db/person")
@Slf4j
public class PersonProtoRestService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(produces = "application/x-protobuf")
    public Flux<Person> getProto() {
        log.info("Listing all persons");
        return Flux.fromIterable(personDao.findAll()).map(toProto).map(Person.Builder::build);
    }

    public Function<io.github.kprasad99.person.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
            Person.Builder.class);
}
