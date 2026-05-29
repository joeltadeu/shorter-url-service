package com.shorterurl.config;

import io.swagger.v3.oas.models.OpenAPI;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI testModuleOpenAPI() {
    return new OpenAPI()
        .info(
            new io.swagger.v3.oas.models.info.Info()
                .title("ShorterURL Service API")
                .version("1.0.0")
                .description(
                    """
                    URL Shortener Service API that converts long URLs into compact, shareable short links.
                    This service receives a valid URL and generates a unique shortened URL that can be easily distributed, stored, and accessed.
                    It also handles redirection from the shortened URL back to the original destination, providing a simple and efficient way to manage links.                                                                                                                                                                                                  This service receives a valid URL and generates a unique shortened URL that can be easily distributed, stored, and accessed. It also handles redirection from the shortened URL back to the original destination, providing a simple and efficient way to manage links.
                    """)
                .contact(
                    new io.swagger.v3.oas.models.info.Contact()
                        .name("Joel Silva")
                        .url("https://github.com/joeltadeu")
                        .email("joeltadeu@gmail.com"))
                .license(
                    new io.swagger.v3.oas.models.info.License()
                        .name("MIT License")
                        .url("https://mit-license.org/")))
        .servers(
            List.of(
                new io.swagger.v3.oas.models.servers.Server()
                    .url("http://localhost:8083")
                    .description("Local Development Server")));
  }
}
