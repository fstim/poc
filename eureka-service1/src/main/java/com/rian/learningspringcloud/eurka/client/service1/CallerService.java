package com.rian.learningspringcloud.eurka.client.service1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@EnableCircuitBreaker
public class CallerService {

    private static final Logger logger = LoggerFactory.getLogger(CallerService.class);

    @Autowired
    private CBCallerService cbCallerService;

    @RequestMapping("/call")
    public ComparaisonReturn call(Integer numberToTest) {
        System.out.println("Appel du service 1");
        ComparaisonReturn result = new ComparaisonReturn();
        result.setInitialValue(numberToTest);
        Integer strangeValue = cbCallerService.getStrange(numberToTest);
        result.setStrangeValue(strangeValue);
        result.setComparaisonResult(numberToTest == strangeValue);
        return result;
    }

}
