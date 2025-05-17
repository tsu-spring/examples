package com.example.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@PropertySources({
        @PropertySource(value = "classpath:/app-config.yml", factory = YamlPropertySourceFactory.class),
        @PropertySource(value = "classpath:/app-config-${spring.profiles.active}.yml",
                factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
})
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotBlank
    private String name;
}
