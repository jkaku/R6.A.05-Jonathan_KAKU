package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "MasterAnnonce API",
                version = "1.0",
                description = "API de gestion d'annonces sécurisée via JAAS"
        ),
        servers = @Server(url = "/dev_avance")
)
public class OpenApiConfig {

}