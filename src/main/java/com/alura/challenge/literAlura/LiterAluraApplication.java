package com.alura.challenge.literAlura;

import com.alura.challenge.literAlura.principal.Principal;
import com.alura.challenge.literAlura.repository.AutoresRepository;
import com.alura.challenge.literAlura.repository.LibrosRepository;
import com.alura.challenge.literAlura.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LibrosRepository librosRepositorio;
	@Autowired
	private AutoresRepository autoresRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librosRepositorio, autoresRepositorio);
		principal.muestraElMenu();
	}
}
