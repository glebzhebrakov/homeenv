package com.homeenv;

import com.homeenv.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties({ApplicationProperties.class})
public class HomeenvApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeenvApplication.class, args);
	}
}
