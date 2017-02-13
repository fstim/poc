package com.rian.learningspringcloud.eurka.client.service1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CallerService {

    private static final Logger logger = LoggerFactory.getLogger(CallerService.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/call")
    public ComparaisonReturn call(Integer numberToTest) {
        ComparaisonReturn result = new ComparaisonReturn();
        result.setInitialValue(numberToTest);
        List<ServiceInstance> springService2Instances = discoveryClient.getInstances("ServiceSpring2");
        if (springService2Instances.isEmpty()) {
            throw new RuntimeException("Pas d'instance de ServiceSpring2 disponible !!!");
        }
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(springService2Instances.get(0).getUri()
                        + "/quelqueChose", Integer.class);
        Integer strangeValue = responseEntity.getBody();
        result.setStrangeValue(strangeValue);
        result.setComparaisonResult(numberToTest == strangeValue);
        return result;
    }

}
