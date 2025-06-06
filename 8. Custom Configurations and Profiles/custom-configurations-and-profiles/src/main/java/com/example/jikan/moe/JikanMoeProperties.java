package com.example.jikan.moe;

import com.example.configuration.YamlPropertySourceFactory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@ConfigurationProperties(prefix = "jikan-moe")
@PropertySources({
        @PropertySource(value="classpath:jikan-moe.yml", factory = YamlPropertySourceFactory.class),
        @PropertySource(value = "classpath:jikan-moe-${spring.profiles.active}.yml",
                factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
})
@Getter
@Setter
public class JikanMoeProperties {

    @NotBlank
    private String topMangaEndpoint;
}
