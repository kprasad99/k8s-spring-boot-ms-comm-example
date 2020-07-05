package io.github.kprasad99.person.client;

import com.google.protobuf.Empty;

import io.github.kprasad99.person.proto.PersonProto.Person;
import io.github.kprasad99.person.proto.PersonServiceGrpc;
import io.grpc.ManagedChannel;

import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PersonClient {

	private final ManagedChannel mc;


	public List<Person> list() {
		return PersonServiceGrpc.newBlockingStub(mc).get(Empty.getDefaultInstance()).getPersonList();
	}

}
