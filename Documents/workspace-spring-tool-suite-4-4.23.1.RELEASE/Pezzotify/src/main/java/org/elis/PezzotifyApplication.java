package org.elis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PezzotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PezzotifyApplication.class, args);
	}

}
