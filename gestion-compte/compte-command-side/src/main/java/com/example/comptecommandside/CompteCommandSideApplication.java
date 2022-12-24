package com.example.comptecommandside;

import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CompteCommandSideApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompteCommandSideApplication.class, args);
	}

	@Bean
	public CommandBus commandBus(){
		return SimpleCommandBus.builder().build();
	}

}
