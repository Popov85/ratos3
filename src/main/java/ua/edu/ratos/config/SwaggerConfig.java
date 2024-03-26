package ua.edu.ratos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "e-Ratos API",
        description = "Sample API for e-Ratos front-end developers",
        version = "1.0",
        contact = @Contact(name = "Andrey P.", url = "https://www.linkedin.com/in/popov85/", email = "andrey.popov85@gmail.com"),
        license = @License(name = "MIT License", url = "https://en.wikipedia.org/wiki/MIT_License")))
public class SwaggerConfig {
}
