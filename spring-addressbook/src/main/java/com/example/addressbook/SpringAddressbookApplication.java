package com.example.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringAddressbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAddressbookApplication.class, args);
	}

}
