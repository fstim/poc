package com.rian.learningspringcloud.eurka.client.service2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class StrangeService {

    @RequestMapping("/quelqueChose")
    public Integer getQuelqueChose() {
        return new Random().nextInt(100);
    }

}
