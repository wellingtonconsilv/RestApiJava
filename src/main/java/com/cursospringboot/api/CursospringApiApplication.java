package com.cursospringboot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.cursospringboot.api.config.security.util.RestApiProperty;


@SpringBootApplication
@EnableConfigurationProperties(RestApiProperty.class)
public class CursospringApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursospringApiApplication.class, args);
	}
}
