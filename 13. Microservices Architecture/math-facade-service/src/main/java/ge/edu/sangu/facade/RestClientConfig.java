package ge.edu.sangu.facade;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // A plain (non-load-balanced) builder. This is the one infrastructure code
    // such as the Eureka client picks up for its own HTTP calls. Without it, our
    // @LoadBalanced builder below would be the only RestClient.Builder in the
    // context, and the Eureka client (which uses RestClient in Spring Cloud
    // 2025.1) would try to load-balance "http://localhost:8761" — treating
    // "localhost" as a service name and failing to register.
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    // The load-balanced builder resolves Eureka logical service names
    // (http://calculator-service). defaultCandidate = false keeps it out of
    // ordinary by-type autowiring, so only injection points that explicitly ask
    // for @LoadBalanced receive it.
    @Bean(defaultCandidate = false)
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(@LoadBalanced RestClient.Builder builder) {
        return builder.baseUrl("http://calculator-service").build();
    }
}
