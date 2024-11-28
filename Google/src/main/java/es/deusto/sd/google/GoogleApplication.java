 package es.deusto.sd.google;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.deusto.sd.google.entity.Credentials;

@SpringBootApplication
public class GoogleApplication {

	public static void main(String[] args) {
		Credentials c = new Credentials("a","a");
		SpringApplication.run(GoogleApplication.class, args);
	}
}
