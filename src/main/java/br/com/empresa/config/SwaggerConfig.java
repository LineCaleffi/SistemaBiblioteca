package br.com.empresa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	// pega do application.properties
	@Value("${app.name}")
	
	private String appName;
	// pega do application.properties	
	@Value("${app.description}")
	private String appDescription;
	
	// pega do application.properties
	@Value("${app.version}")
	private String appVersion;
	
	//cria uma API
	public OpenAPI openAPI() {
		// configura o nom, a descrição e a versão
		return new OpenAPI()
				.info(new Info()
				.title(appName)
				.description(appDescription)
				.version(appVersion));
	}
}

/** 
 * O Swagger basicamente é para criar uma API para enxergar tudo que tem no sistema.
 * Fornece o endereço de URL 
 * No caso -> http://localhost:8080/api-sistema/swagger-ui/index.html#
**/