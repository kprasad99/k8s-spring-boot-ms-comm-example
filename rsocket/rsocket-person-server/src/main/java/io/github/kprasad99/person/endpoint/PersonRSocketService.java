package io.github.kprasad99.person.endpoint;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import io.github.kprasad99.person.orm.dao.PersonDao;
import io.github.kprasad99.person.proto.PersonProto.Person;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Controller
@Slf4j
public class PersonRSocketService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ModelMapper mapper;

    @MessageMapping("io.github.kprasad99.proto.person")
    public Flux<Person> getProto() {
        log.info("Listing all persons");
        return Flux.fromIterable(personDao.findAll()).map(toProto).map(Person.Builder::build).subscribeOn(Schedulers.boundedElastic());
    }

    public Function<io.github.kprasad99.person.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
            Person.Builder.class);
}
