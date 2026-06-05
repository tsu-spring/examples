package ge.edu.sangu.facade;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FacadeController {

    private final CalculatorClient calculatorClient;
    private final List<String> history = new CopyOnWriteArrayList<>();

    public FacadeController(CalculatorClient calculatorClient) {
        this.calculatorClient = calculatorClient;
    }

    @GetMapping("/calculate")
    public String calculate(@RequestParam int a,
                            @RequestParam int b,
                            @RequestParam(defaultValue = "add") String op) {
        Integer result = calculatorClient.calculate(op, a, b);

        if (result == null) {
            return "calculator-service is unavailable (circuit breaker fallback)";
        }

        String record = String.format("%d %s %d = %d", a, op, b, result);
        history.add(record);
        return "Result from calculator-service: " + record;
    }

    @GetMapping("/history")
    public List<String> getHistory() {
        return history;
    }
}
