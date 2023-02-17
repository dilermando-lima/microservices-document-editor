package apicore.environment;

import java.util.Properties;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class PropertiesDefault extends PropertySourcesPlaceholderConfigurer {

    @SuppressWarnings("java:S1075")
    public static final String PREFIX_SWAGGER_PATH = "/swagger";

    public PropertiesDefault(){
        setIgnoreUnresolvablePlaceholders(true);
        setProperties(getDefaultProperties());
    }

    private Properties getDefaultProperties(){

        var properties = new Properties();

        // appliaction server
        properties.setProperty("server.port", "${${spring.application.name}.server.port}");

        // database connection
        properties.setProperty("spring.data.mongodb.host", "${${spring.application.name}.spring.data.mongodb.host}");
        properties.setProperty("spring.data.mongodb.port", "${${spring.application.name}.spring.data.mongodb.port}");
        properties.setProperty("spring.data.mongodb.database", "${${spring.application.name}.spring.data.mongodb.database}");
        properties.setProperty("spring.data.mongodb.username", "${${spring.application.name}.spring.data.mongodb.username}");
        properties.setProperty("spring.data.mongodb.password", "${${spring.application.name}.spring.data.mongodb.password}");
        properties.setProperty("spring.data.mongodb.authentication-database", "${${spring.application.name}.spring.data.mongodb.authentication-database}");

        // handle 404 mapping response
        properties.setProperty("spring.mvc.throw-exception-if-no-handler-found","true");
        properties.setProperty("spring.web.resources.add-mappings","false");

        // default path swagger
        properties.setProperty("springdoc.swagger-ui.path", PREFIX_SWAGGER_PATH);
        properties.setProperty("springdoc.api-docs.path", PREFIX_SWAGGER_PATH + "/api-docs");

        return properties;

    }



    
}
