package io.github.kprasad99.person.endpoint;

import io.github.kprasad99.person.orm.dao.PersonDao;
import io.github.kprasad99.person.proto.PersonProto.Person;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proto/db/person")
@Slf4j
public class PersonRestProtoService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> list() {
        log.info("Listing all persons");
        return personDao.findAll().stream().map(toProto).map(Person.Builder::build).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Person findById(@PathVariable("id") int id) {
        log.info("Requesting data for person with id {}", id);
        return personDao.findById(id).map(toProto).map(Person.Builder::build).orElse(null);
    }

    public Function<io.github.kprasad99.person.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
            Person.Builder.class);

}
