package com.rian.learningspringcloud.eurka.client.service1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class EurekaService1Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaService1Application.class, args);
	}

	@Bean
	CommandLineRunner init(DiscoveryClient discoveryClient) {
		return args -> {
			System.out.println("Démarrage de l'application TOTO");
			List<ServiceInstance> services = discoveryClient.getInstances("ServiceSpring2");
			for (ServiceInstance service : services) {
				System.out.println(service.getUri());
			}
		};
	}

}
