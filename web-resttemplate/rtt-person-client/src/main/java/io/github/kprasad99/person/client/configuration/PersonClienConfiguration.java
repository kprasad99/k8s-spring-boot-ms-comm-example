package io.github.kprasad99.person.client.configuration;

import io.github.kprasad99.person.client.configuration.ServiceInfo.ClientInfo;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableConfigurationProperties(ServiceInfo.class)
public class PersonClienConfiguration {

    @ConditionalOnProperty(prefix = "spring.main", name = "cloud-platform", havingValue = "kubernetes")
    @Bean
    public RestTemplate restTemplate(DiscoveryClient client, ServiceInfo serviceInfo) {

        ClientInfo service = serviceInfo.getClients().get("person");
        RestTemplate restTemplate = new RestTemplate();
        Optional<ServiceInstance> instance = Optional.ofNullable(client.getInstances(service.getName()))
                .filter(Objects::nonNull).stream().flatMap(e -> e.stream())
                .filter(e -> service.getName().equals(e.getServiceId())).findFirst();
        URI url = instance.map(ServiceInstance::getUri).orElseThrow();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(url.toString()));
        return restTemplate;
    }

    @ConditionalOnMissingBean
    @ConditionalOnProperty("services.clients.person.url")
    @Bean
    public RestTemplate restTemplateUrl(ServiceInfo serviceInfo) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate
                .setUriTemplateHandler(new DefaultUriBuilderFactory(serviceInfo.getClients().get("person").getUrl()));
        return restTemplate;
    }
}
