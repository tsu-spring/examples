package ge.edu.sangu.facade;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CalculatorClient {

    private static final Logger log = LoggerFactory.getLogger(CalculatorClient.class);

    private final RestClient restClient;

    public CalculatorClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // Calls calculator-service through service discovery. If it is unreachable,
    // Resilience4j trips the circuit and routes to the fallback method.
    @CircuitBreaker(name = "calculatorService", fallbackMethod = "calculateFallback")
    public Integer calculate(String op, int a, int b) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/math/" + op)
                        .queryParam("a", a)
                        .queryParam("b", b)
                        .build())
                .retrieve()
                .body(Integer.class);
    }

    public Integer calculateFallback(String op, int a, int b, Throwable throwable) {
        log.warn("calculator-service unavailable ({}). Returning fallback for {} {} {}",
                throwable.getMessage(), a, op, b);
        return null;
    }
}
