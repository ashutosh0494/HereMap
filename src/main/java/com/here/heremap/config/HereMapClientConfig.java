package com.here.heremap.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "com.here.map")
@Validated
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HereMapClientConfig {

    String apiKey;
    String geoCodeUrl;
    String searchPlaceUrl;
    String searchLimit;


    /**
     * Bean for creating RestTemplate.
     */
    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();

    }

}
