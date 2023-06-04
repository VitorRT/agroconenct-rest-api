package br.com.smashcode.api.agroconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class DocumentationConfig {
    @Bean
    public OpenAPI customOpenAPI() {
      return new OpenAPI()
             .info(new Info()
                .title("Agro Connect Rest API 🌐")
                .version("v1")
                .contact(new Contact().name("Smash Code! 👨🏾‍💻").email("project.smashcode@gmail.com"))
                .description("API Rest de acesso ao sistema da Agro Connect!")
             )
             .components(new Components()
             .addSecuritySchemes("bearer-key",
             new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
   }
}
