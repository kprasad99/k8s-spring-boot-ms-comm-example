package io.github.kprasad99.person.client;

import io.github.kprasad99.person.client.DiscoveryInfo.ServiceInfo;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(DiscoveryInfo.class)
public class PersonClienConfiguration {

    private static final String SERVICE_NAME = "person-flux";

    @ConditionalOnProperty(prefix = "spring.main", name = "cloud-platform", havingValue = "kubernetes")
    @Bean
    public WebClient webClient(DiscoveryClient client, DiscoveryInfo serviceInfo) {

        ServiceInfo service = serviceInfo.getServices().get(SERVICE_NAME);
        Optional<ServiceInstance> instance = Optional.ofNullable(client.getInstances(service.getName()))
                .filter(Objects::nonNull).stream().flatMap(List::stream)
                .filter(e -> service.getName().equals(e.getServiceId())).findFirst();
        URI url = instance.map(ServiceInstance::getUri).orElseThrow();
        return WebClient.create(url.toString());
    }

    @ConditionalOnMissingBean
    @ConditionalOnProperty("discovery.services.person-flux.url")
    @Bean
    public WebClient webClientUrl(DiscoveryInfo serviceInfo) {
        String url = serviceInfo.getServices().get(SERVICE_NAME).getUrl();
        return WebClient.create(url);
    }
}
