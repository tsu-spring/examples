package com.example.jikan.moe;

import com.example.configuration.YamlPropertySourceFactory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@ConfigurationProperties(prefix = "jikan-moe")
@PropertySource(value = "classpath:jikan-moe.yml", factory = YamlPropertySourceFactory.class)
@Getter
@Setter
public class JikanMoeProperties {

    @NotBlank
    private String topMangaEndpoint;
}
