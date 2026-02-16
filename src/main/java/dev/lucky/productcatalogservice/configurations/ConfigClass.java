package dev.lucky.productcatalogservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigClass {
    /* In Configurations,
    we tell spring to create some library object and keep it / manage it's lifecycle.
    */

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
