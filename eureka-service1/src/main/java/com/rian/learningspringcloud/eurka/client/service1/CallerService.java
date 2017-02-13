package com.rian.learningspringcloud.eurka.client.service1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableCircuitBreaker
@RefreshScope
public class CallerService {

    private static final Logger logger = LoggerFactory.getLogger(CallerService.class);

    @Value("${message:Hello default}")
    private String message;

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

    @RequestMapping("/propertyCloud")
    public String call() {
        System.out.println("Appel du service message");
        return this.message;
    }

}
