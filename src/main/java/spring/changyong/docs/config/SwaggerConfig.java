package spring.changyong.docs.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI(){
		Info info = new Info()
				.version("v0.1")
				.title("Review Eye API")
				.description("리뷰 아이 API");
		return new OpenAPI().info(info);
	}

//	@Bean
//	public OpenAPI openAPI(){
//		String securityJwtName = "JWT";
//		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
//		Components components = new Components()
//				.addSecuritySchemes(
//						securityJwtName, new SecurityScheme().name(securityJwtName)
//								.type(SecurityScheme.Type.HTTP)
//								.scheme("Bearer")
//								.bearerFormat(securityJwtName)
//				);
//
//		return new OpenAPI().addSecurityItem(securityRequirement).components(components);
//	}

}
