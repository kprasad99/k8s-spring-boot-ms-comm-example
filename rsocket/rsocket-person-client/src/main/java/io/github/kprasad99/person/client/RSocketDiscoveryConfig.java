package io.github.kprasad99.person.client;

import io.github.kprasad99.person.client.DiscoveryInfo.ServiceInfo;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;

import java.util.List;
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
@EnableConfigurationProperties(DiscoveryInfo.class)
public class RSocketDiscoveryConfig {

    private static final String SERVICE_NAME = "person-rsocket";

    @ConditionalOnProperty(prefix = "spring.main", name = "cloud-platform", havingValue = "kubernetes")
    @Bean
    public ClientTransport clientTransport(DiscoveryClient client, DiscoveryInfo discover) {

        ServiceInfo service = discover.getServices().get(SERVICE_NAME);
        Optional<ServiceInstance> instance = Optional.ofNullable(client.getInstances(service.getName()))
                .filter(Objects::nonNull).stream().flatMap(List::stream)
                .filter(e -> service.getName().equals(e.getServiceId())).findFirst();
        if (service.getServicePort() != null && !service.getServicePort().isBlank()) {
            return instance.map(e -> {
                int port = Integer.parseInt(e.getMetadata().get(service.getServicePort()));
                String host = e.getHost();
                return TcpClientTransport.create(host, port);
            }).orElseThrow();
        } else {
            return instance.map(e -> TcpClientTransport.create(e.getHost(), e.getPort())).orElseThrow();
        }
    }

    @ConditionalOnMissingBean
    @ConditionalOnProperty("discovery.services.person-rsocket.port")
    @Bean
    public ClientTransport clientTransportPort(DiscoveryInfo serviceInfo) {
        return TcpClientTransport.create(serviceInfo.getServices().get(SERVICE_NAME).getHost(),
                serviceInfo.getServices().get(SERVICE_NAME).getPort());
    }

}
