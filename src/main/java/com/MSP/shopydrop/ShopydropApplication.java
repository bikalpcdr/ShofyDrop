package com.MSP.shopydrop;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShopydropApplication {

	public static void main(String[] args) {

		// to load env variables from .env file
		Dotenv dotenv = Dotenv.load();

		// set system properties
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("DB_API_DOC_PATH", dotenv.get("DB_API_DOC_PATH"));
		System.setProperty("DB_SWAGGER_UI_PATH", dotenv.get("DB_SWAGGER_UI_PATH"));

		SpringApplication.run(ShopydropApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
