package me.plurg.creator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI baseApi(){

        ApiResponse error = new ApiResponse()
                .content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value("\"code\": 400, \"status:\" \"Bad request\", \"message:\" something went wrong!")
                        )))
                .description("Error");

        Components components = new Components();
        components.addResponses("bad_request", error);

        Contact contact = new Contact();
        contact.name("Confidence Dev");
        contact.url("https://github.com/ConfidenceDev");
        contact.email("confidostic3@gmail.com");

        Info info = new Info()
                .title("Blog Docs: Creator")
                .description("All available endpoints for article service")
                .version("1.0.0")
                .contact(contact);
        return new OpenAPI().components(components).info(info);
    }
}
