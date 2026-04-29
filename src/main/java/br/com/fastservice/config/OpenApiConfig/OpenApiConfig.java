package br.com.fastservice.config.OpenApiConfig;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI fastServiceOpenAPI() {
		return new OpenAPI().info(new Info().title("FastService API").version("v1")
				.description("API para gerenciamento de clientes e prestadores de serviço.")
				.contact(new Contact().name("FastService").email("suporte@fastservice.com")));
	}

	@Bean
	public GroupedOpenApi clientesApi() {
		return GroupedOpenApi.builder().group("Clientes").packagesToScan("br.com.fastservice.controller")
				.pathsToMatch("/api/clientes", "/api/clientes/**").build();
	}

	@Bean
	public GroupedOpenApi prestadoresApi() {
		return GroupedOpenApi.builder().group("Prestadores").packagesToScan("br.com.fastservice.controller")
				.pathsToMatch("/api/prestadores", "/api/prestadores/**").build();
	}
}
