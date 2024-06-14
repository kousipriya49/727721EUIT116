package com.example.demo;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NumberService {
	 private static final String NUMBER_SOURCE_URL = "http://example.com/numbers/{type}";
	    private static final int TIMEOUT = 500;

	    public List<Integer> fetchNumbers(String numberType) {
	        RestTemplate restTemplate = new RestTemplate();
	        try {
	            // This is a placeholder URL and should be replaced with the actual third-party API URL
	            String url = NUMBER_SOURCE_URL.replace("{type}", numberType);
	            Integer[] numbers = restTemplate.getForObject(url, Integer[].class);
	            if (numbers != null) {
	                return List.of(numbers);
	            }
	        } catch (Exception e) {
	            // Log the exception (not shown here for brevity)
	        }
	        return Collections.emptyList();
	    }
}
