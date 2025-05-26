package com.itm.space.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${keycloak.token-url}")
    private String tokenUrl;

    @Value("${keycloak.refresh-url}")
    private String refreshUrl;

    @Value("${project.version}")
    private String appVersion;

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme keycloakScheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                                .tokenUrl(tokenUrl)
                                .refreshUrl(refreshUrl)
                        )
                );

        return new OpenAPI()
                .info(new Info()
                        .title("Application service")
                        .description("Сервис, отвечающий за процессы с заявками")
                        .version(appVersion))
                .components(new Components()
                        .addSecuritySchemes("keycloak_oauth_scheme", keycloakScheme));
    }
}
