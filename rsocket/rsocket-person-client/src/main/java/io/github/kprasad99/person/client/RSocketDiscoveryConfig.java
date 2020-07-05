package io.github.kprasad99.person.client;

import io.github.kprasad99.person.client.ServiceInfo.ClientInfo;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;

import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
@EnableConfigurationProperties(ServiceInfo.class)
public class RSocketDiscoveryConfig {

    @ConditionalOnProperty(prefix = "spring.main", name = "cloud-platform", havingValue = "kubernetes")
    @Bean
    public ClientTransport clientTransport(DiscoveryClient client, ServiceInfo serviceInfo) {

        ClientInfo service = serviceInfo.getClients().get("person");
        Optional<ServiceInstance> instance = Optional.ofNullable(client.getInstances(service.getName()))
                .filter(Objects::nonNull).stream().flatMap(e -> e.stream())
                .filter(e -> service.getName().equals(e.getServiceId())).findFirst();
        if (service.getPort() != null && !service.getPort().isBlank()) {
            return instance.map(e -> {
                int port = Integer.parseInt(e.getMetadata().get(service.getPort()));
                String host = e.getHost();
                return TcpClientTransport.create(host, port);
            }).orElseThrow();
        } else {
            return instance.map(e -> TcpClientTransport.create(e.getHost(), e.getPort())).orElseThrow();
        }
    }

    @ConditionalOnMissingBean
    @ConditionalOnProperty("services.clients.person.port")
    @Bean
    public ClientTransport clientTransportPort(ServiceInfo serviceInfo) {
        return TcpClientTransport.create(serviceInfo.getClients().get("person").getHost(),
                Integer.parseInt(serviceInfo.getClients().get("person").getPort()));
    }

}
