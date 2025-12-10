package br.com.curriculo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "API - Gerador de currículos",
				version = "1.0",
				description = "API para geração de curriculos",
				contact = @Contact(name = "Carlos Roberto", email = "crrsj1@gmail.com")
		)
)
public class CurriculoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurriculoApplication.class, args);
	}

}
