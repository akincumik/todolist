package com.akin.todolist;


import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(ListItemRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new ListItem("Name1", "UI Design", new Date(), "onGoing")));
			log.info("Preloading " + repository.save(new ListItem("Name2", "Back-end Design", new Date(),"todo")));
		};
	}
}
