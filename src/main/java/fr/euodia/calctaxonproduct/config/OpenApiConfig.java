package fr.euodia.calctaxonproduct.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Calcul des Taxes sur Produits")
                        .description("API REST pour la gestion des produits et le calcul des taxes selon les pays. " +
                                "Cette API permet de créer des produits, les récupérer et calculer leur prix final avec les taxes appropriées.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Équipe Euodia")
                                .email("support@euodia.fr"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
