package com.example.ticketKing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TicketKingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketKingApplication.class, args);
		System.out.println();

	}


}
