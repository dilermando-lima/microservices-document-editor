package apicore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import apicore.access.AccessIntercept;
import apicore.access.JwtUtils;
import apicore.environment.PropertiesDefault;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class ConfigApi {

    @Bean
    public static PropertySourcesPlaceholderConfigurer handleGlobalProperties(){
        return new PropertiesDefault();
    } 

  
    @Bean
    public OpenAPI setGlobalAuthorizationHeaderIntoSwagger() {
      return new OpenAPI().components(
                new Components()
                    .addSecuritySchemes(
                        JwtUtils.NAME_HEADER_AUTHORIZATION, 
                        new SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .in(SecurityScheme.In.HEADER)
                            .name(JwtUtils.NAME_HEADER_AUTHORIZATION)
                    ))
                    .addSecurityItem(new SecurityRequirement().addList(JwtUtils.NAME_HEADER_AUTHORIZATION)
            );
   }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(@Autowired AccessIntercept accessIntercept)
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST","PUT","DELETE","OPTIONS","PATCH")
                        .allowedHeaders("Access-Control-Allow-Headers","X-Requested-With","Authorization","Content-Type","X-File-Name")
                        .exposedHeaders("Access-Control-Expose-Headers", "X-File-Name", "Content-Disposition")
                        .allowCredentials(true)
                        .maxAge(3600); 
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry
                    .addInterceptor(accessIntercept)
                    .excludePathPatterns(
                        PropertiesDefault.PREFIX_SWAGGER_PATH +  "/**"
                    );
            }
        };
    }
    
}
