package io.github.kprasad99.person.client;

import io.github.kprasad99.person.proto.PersonProto.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonRSocketClient {

    @Autowired
    private Mono<RSocketRequester> personClient;

    public Flux<Person> list() {
//		return personClient.log().flatMapMany(rsocket -> {
//			rsocket.rsocket().onClose().doOnError(error -> log.warn("Connection CLOSED"))
//					.doFinally(consumer -> log.info("Client DISCONNECTED")).subscribe();
//			return rsocket.route("io.github.kprasad99.proto.person").retrieveFlux(Person.class);
//		});
        return personClient
                .flatMapMany(rsocket -> rsocket.route("io.github.kprasad99.proto.person").retrieveFlux(Person.class));
    }

}
