package io.handsone.tasktracker.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(title = "Task-Tracker API", version = "0.0.1", description = "Specs of Task-Tracker service."),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local"),
//        @Server(url = "https://handsone.o-r.kr", description = "Dev"),
    },
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    name = "bearerAuth",
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenAPIConfiguration {

}
