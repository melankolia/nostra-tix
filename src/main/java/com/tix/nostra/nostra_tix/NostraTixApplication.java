package com.tix.nostra.nostra_tix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NostraTixApplication {

	public static void main(String[] args) {
		SpringApplication.run(NostraTixApplication.class, args);
	}

}
