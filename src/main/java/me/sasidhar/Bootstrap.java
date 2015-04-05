package me.sasidhar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import me.sasidhar.healthtracker.domain.mongo.UnitReadConverter;
import me.sasidhar.healthtracker.domain.mongo.UnitWriteConverter;
import me.sasidhar.healthtracker.rest.HealthMetricsEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Bootstrap extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Bootstrap.class, JerseyConfig.class);

    }

    @Component
    static class JerseyConfig extends ResourceConfig {
        public JerseyConfig() {
            register(HealthMetricsEndpoint.class);

            JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            jacksonJsonProvider.setMapper(objectMapper);
            register(jacksonJsonProvider);
        }
    }

    @Bean
    public List<Converter<?, ?>> customMongoConversions() {
        return Arrays.asList(new UnitReadConverter(), new UnitWriteConverter());
    }

}