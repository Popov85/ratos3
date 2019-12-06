package ua.edu.ratos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("ua.edu.ratos"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "e-Ratos API",
                "Sample API for e-Ratos front-end developers",
                "1.0",
                "Free to use",
                new Contact("Andrey P.", "https://www.linkedin.com/in/andrey-popov-40101998/", "andrey.popov85@gmail.com"),
                "MIT License",
                "https://en.wikipedia.org/wiki/MIT_License",
                Collections.emptyList());
    }
}
