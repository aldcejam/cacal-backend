package br.com.minhascontas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI minhasContasOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Minhas Contas API")
                        .description("API validation for Minhas Contas Application")
                        .version("1.0"));
    }
}
