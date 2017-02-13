package com.rian.learningspringcloud.eurka.client.service1;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CBCallerService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public CBCallerService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @HystrixCommand(fallbackMethod = "reliable")
    public Integer getStrange(Integer numberToTest) {
        System.out.println("Appel du service via le CB");
        List<ServiceInstance> springService2Instances = discoveryClient.getInstances("ServiceSpring2");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(springService2Instances.get(0).getUri()
                + "/quelqueChose", Integer.class);
        return responseEntity.getBody();
    }

    public Integer reliable(Integer numberToTest) {
        return numberToTest;
    }

}
