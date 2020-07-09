package io.github.kprasad99.person.client;

import io.micrometer.core.instrument.MeterRegistry;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.ClientTransport;

import java.time.Duration;

import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
public class RSocketClientConfig {

    @Bean
    public Mono<RSocketRequester> rSocketRequester(MeterRegistry meterRegistry,
            RSocketRequester.Builder rsocketRequesterBuilder, ClientTransport clientTransport,
            RSocketStrategies strategies) {
        Mono<RSocketRequester> rsocketRequester = rsocketRequesterBuilder.rsocketStrategies(strategies)
                .dataMimeType(new MimeType("application", "x-protobuf"))
                .rsocketConnector(new RSocketConnectorConfigurer() {

                    @Override
                    public void configure(RSocketConnector connector) {
                        connector.reconnect(Retry.backoff(5, Duration.ofMillis(2500)));
                    }
                }).connect(clientTransport).log();
//      rsocketRequester.rsocket().onClose().doOnError(error -> log.warn("Connection CLOSED"))
//              .doFinally(consumer -> log.info("Client DISCONNECTED")).subscribe();
        return rsocketRequester;
    }

    @Bean
    public RSocketStrategiesCustomizer protobufRSocketStrategyCustomizer() {
        return (strategy) -> {
            strategy.decoder(new ProtobufDecoder());
            strategy.encoder(new ProtobufEncoder());
        };
    }

}
