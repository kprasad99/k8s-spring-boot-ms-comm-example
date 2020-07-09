package io.github.kprasad99.person.client;

import java.util.Map;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "discovery")
@Data
public class DiscoveryInfo {

    private Map<String, ServiceInfo> services;

    @Data
    public static class ServiceInfo {
        private String name;
        private String servicePort;
        private String host;
        private int port;
        private String url;
    }

}
