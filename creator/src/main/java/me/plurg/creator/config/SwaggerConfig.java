package me.plurg.creator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI baseApi(){
        Contact contact = new Contact();
        contact.name("Confidence Dev");
        contact.url("https://github.com/ConfidenceDev");
        contact.email("confidostic3@gmail.com");

        Info info = new Info()
                .title("Blog Docs: Creator")
                .description("All available endpoints for article service")
                .version("1.0.0")
                .contact(contact);
        return new OpenAPI().info(info);
    }
}
