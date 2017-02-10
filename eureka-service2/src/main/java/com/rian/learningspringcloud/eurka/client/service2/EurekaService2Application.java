package com.rian.learningspringcloud.eurka.client.service2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class EurekaService2Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaService2Application.class, args);
	}

    @Bean
    CommandLineRunner init() {
        return args -> System.out.println("Démarrage de l'application ServiceSpring2");
    }
}
