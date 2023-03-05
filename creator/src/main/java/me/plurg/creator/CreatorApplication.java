package me.plurg.creator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CreatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatorApplication.class, args);
	}

	@Bean
	//@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
