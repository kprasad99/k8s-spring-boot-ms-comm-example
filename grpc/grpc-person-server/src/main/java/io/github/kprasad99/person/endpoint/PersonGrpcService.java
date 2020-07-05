package io.github.kprasad99.person.endpoint;

import com.google.protobuf.Empty;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.lognet.springboot.grpc.GRpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.kprasad99.person.orm.dao.PersonDao;
import io.github.kprasad99.person.proto.PersonProto.Person;
import io.github.kprasad99.person.proto.PersonProto.Persons;
import io.github.kprasad99.person.proto.PersonServiceGrpc.PersonServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@GRpcService
@Slf4j
public class PersonGrpcService extends PersonServiceImplBase {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void get(Empty request, StreamObserver<Persons> response) {
        log.info("Listing all persons");
        List<Person> items = personDao.findAll().stream().map(toProto).map(Person.Builder::build)
                .collect(Collectors.toList());
        Persons persons = Persons.newBuilder().addAllPerson(items).build();
        response.onNext(persons);
        response.onCompleted();
    }

    public Function<io.github.kprasad99.person.orm.model.Person, Person.Builder> toProto = p -> mapper.map(p,
            Person.Builder.class);
}
