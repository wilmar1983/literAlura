package com.Challenge.literAlura;


import com.Challenge.literAlura.principal.Principal; // Importamos la nueva clase Principal
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private Principal principal; // Inyectamos nuestra clase Principal

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Este método se ejecuta automáticamente cuando la aplicación Spring Boot arranca.
		principal.muestraElMenu(); // Llamamos al método que mostrará el menú
	}
}