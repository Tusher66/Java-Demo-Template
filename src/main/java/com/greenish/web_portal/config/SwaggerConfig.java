package com.greenish.web_portal.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setName("Greenish Web App (Java Team)");
        contact.setUrl("https://site.greenish.com/");
        contact.setEmail("api.docs@co.greenish.com");

//        License mitLicense = new License().name("MIT License").url("https://www.example.com/licenses/mit/");

        Info info = new Info()
                .title("Web Portal (Java)")
                .version("1.0.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Web Portal System (Java).")
                .termsOfService("https://site.greenish.com/");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        Components components = new Components()
                .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
