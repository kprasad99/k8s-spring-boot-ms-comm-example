package io.github.kprasad99.person.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

@Configuration
public class AppConfig {

    @Bean
    public ProtobufJsonFormatHttpMessageConverter protobufConverter() {
        return new ProtobufJsonFormatHttpMessageConverter();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
