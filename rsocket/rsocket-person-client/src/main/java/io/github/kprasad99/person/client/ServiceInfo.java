package io.github.kprasad99.person.client;

import java.util.Map;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "services")
@Data
public class ServiceInfo {

    private Map<String, ClientInfo> clients;

    @Data
    public static class ClientInfo {
        private String name;
        private String port;
        public String host;
    }

}
