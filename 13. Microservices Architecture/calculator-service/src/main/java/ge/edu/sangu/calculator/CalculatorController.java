package ge.edu.sangu.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class CalculatorController {

    private static final Logger log = LoggerFactory.getLogger(CalculatorController.class);

    // Value sourced from the Config Server (calculator-service.properties).
    // Falls back to a literal if the config server did not provide it.
    @Value("${calculator.greeting:default greeting (config server not used)}")
    private String greeting;

    @GetMapping("/add")
    public int add(@RequestParam int a, @RequestParam int b) {
        log.info("calculator-service handling: {} + {}", a, b);
        return a + b;
    }

    @GetMapping("/subtract")
    public int subtract(@RequestParam int a, @RequestParam int b) {
        log.info("calculator-service handling: {} - {}", a, b);
        return a - b;
    }

    @GetMapping("/multiply")
    public int multiply(@RequestParam int a, @RequestParam int b) {
        log.info("calculator-service handling: {} * {}", a, b);
        return a * b;
    }

    @GetMapping("/divide")
    public int divide(@RequestParam int a, @RequestParam int b) {
        log.info("calculator-service handling: {} / {}", a, b);
        return a / b;
    }

    @GetMapping("/greeting")
    public String greeting() {
        log.info("calculator-service serving greeting from config: {}", greeting);
        return greeting;
    }
}
