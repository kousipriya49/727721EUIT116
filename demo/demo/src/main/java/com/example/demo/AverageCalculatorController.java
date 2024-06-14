package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/numbers")
public class AverageCalculatorController {

    private static final int WINDOW_SIZE = 10;
    private final Deque<Integer> window = new ConcurrentLinkedDeque<>();
    private final NumberService numberService;

    @Autowired
    public AverageCalculatorController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping("/{numberType}")
    public Map<String, Object> getNumbers(@PathVariable String numberType) {
        Map<String, Object> response = new HashMap<>();
        List<Integer> previousState = new ArrayList<>(window);
        List<Integer> fetchedNumbers = numberService.fetchNumbers(numberType);

        for (Integer number : fetchedNumbers) {
            if (!window.contains(number)) {
                if (window.size() >= WINDOW_SIZE) {
                    window.poll();
                }
                window.add(number);
            }
        }

        List<Integer> currentState = new ArrayList<>(window);

        double average = window.stream()
                               .mapToInt(Integer::intValue)
                               .average()
                               .orElse(0.0);

        response.put("windowPrevState", previousState);
        response.put("windowCurrState", currentState);
        response.put("numbers", fetchedNumbers);
        response.put("avg", String.format("%.2f", average));

        return response;
    }
}