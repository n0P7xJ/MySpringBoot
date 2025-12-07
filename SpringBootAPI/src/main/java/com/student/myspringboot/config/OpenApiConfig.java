package com.student.myspringboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MySpringBoot REST API")
                        .version("1.0")
                        .description("REST API для роботи з React додатком. Підтримка продуктів, категорій та користувачів.")
                        .contact(new Contact()
                                .name("Student")
                                .email("student@example.com")));
    }
}
