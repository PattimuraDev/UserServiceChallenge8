package org.example.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * kelas untuk representasi konfigurasi swagger ui
 * @author Dwi Satria Patra
 */
@Configuration
public class SwaggerConfig {

    /**
     * Method untuk mengatur konfigurasi swagger dengan OpenAPI versi 3
     * @param appDescription Deskripsi dari dokumentasi swagger
     * @param appVersion Keterangan versi dari dokumentasi dengan swagger
     * @return OpenAPI yang mengatur detail dari dokumentasi swagger
     */
    @Bean
    public OpenAPI demoApi(@Value("REST API user microservice for challenge chapter 7 backend java Binar Academy") String appDescription,
                           @Value("v1.0.0") String appVersion
    ) {
        final String securitySchemeName = "bearerAuth";
//        Server server = new Server();
//        server.setUrl("https://microserviceuserchallenge7-production.up.railway.app/");
//        List<Server> listOfServer = new ArrayList<>();
//        listOfServer.add(server);

        return new OpenAPI()
                .info(new Info()
                        .title("User Microservice Endpoints")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        )
                )
                //.servers(listOfServer)
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName)
                )
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .description("Masukkan token yang sudah digenerate di endpoint api/auth/signin")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
