package com.vitisoft.backend;

import org.springframework.boot.CommandLineRunner;
// Import necessary Spring Boot classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.service.LoginManager;

/**
 * Main class for the Spring Boot application.
 * 
 * The @SpringBootApplication annotation is a combination of:
 * - @Configuration: Marks this class as a source of Spring configuration
 * - @EnableAutoConfiguration: Enables Spring Boot’s auto-configuration
 * - @ComponentScan: Automatically scans this package and sub-packages for
 * Spring components (e.g., @Service, @Repository)
 */
@SpringBootApplication
public class SpringbootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAppApplication.class, args);
	}

	/**
	 * This will run after Spring Boot finishes starting.
	 * Used for testing DB connection and login logic.
	 */
	@Bean
	public CommandLineRunner runTest() {
		return args -> {
			// Connect to the database
			DatabaseManager db = new DatabaseManager();
			db.connect(); // Ensure this method works with your MySQL + Spring setup
			db.test(); // Optional: test query or sample operation

			// Set up login manager (if needed)
			LoginManager lm = new LoginManager(db);

			// Optional test call: lm.logIn("admin", "password");
		};
	}
}
