package com.concredito.prospects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.concredito.prospects")
public class ProspectsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProspectsApplication.class, args);
	}

}
