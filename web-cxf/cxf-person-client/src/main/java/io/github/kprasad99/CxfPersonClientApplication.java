package io.github.kprasad99;

import org.apache.cxf.jaxrs.client.spring.EnableJaxRsProxyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJaxRsProxyClient
public class CxfPersonClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxfPersonClientApplication.class, args);
	}

}
