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

    @RequestMapping("/call")
    public ComparaisonReturn call(@RequestParam(value = "numberToTest") Integer numberToTest) {
        logger.info("Appel de la méthode call du service spring 1!");
        ComparaisonReturn result = new ComparaisonReturn();
        result.setInitialValue(numberToTest);
        List<ServiceInstance> springService2Instances = discoveryClient.getInstances("ServiceSpring2");
        if (springService2Instances.isEmpty()) {
            throw new RuntimeException("Pas d'instance de ServiceSpring2 disponible !!!");
        }
        springService2Instances.forEach(springService2Instance -> logger.debug(springService2Instance.getUri().toString()));
        ResponseEntity<Integer> responseEntity = new RestTemplate().getForEntity(springService2Instances.get(0).getUri()
                        + "/quelqueChose", Integer.class);
        Integer strangeValue = responseEntity.getBody();
        logger.debug("Valeur récupérée : " + strangeValue);
        result.setStrangeValue(strangeValue);
        result.setComparaisonResult(numberToTest == strangeValue);
        logger.debug("Le résultat de l'appel est : " + result.getComparaisonResult() + " pour la valeur initiale : "
                + result.getInitialValue() + " et la valeur étrange : " + result.getStrangeValue());
        return result;
    }

}
