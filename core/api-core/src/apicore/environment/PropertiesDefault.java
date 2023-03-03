package apicore.environment;

import java.util.Properties;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class PropertiesDefault extends PropertySourcesPlaceholderConfigurer {

    @SuppressWarnings("java:S1075")
    public static final String PREFIX_SWAGGER_PATH = "/swagger";

    public PropertiesDefault(){
        setIgnoreUnresolvablePlaceholders(true);
        setOrder(HIGHEST_PRECEDENCE);
        setProperties(getDefaultProperties());
    }

    private Properties getDefaultProperties(){

        var properties = new Properties();

        // disable spring aop
        properties.setProperty("spring.aop.auto","false");
        properties.setProperty("spring.aop.proxy-target-class","false");

        // handle 404 mapping response
        properties.setProperty("spring.mvc.throw-exception-if-no-handler-found","true");
        properties.setProperty("spring.web.resources.add-mappings","false");

        // default path swagger
        properties.setProperty("springdoc.swagger-ui.path", PREFIX_SWAGGER_PATH);
        properties.setProperty("springdoc.api-docs.path", PREFIX_SWAGGER_PATH + "/api-docs");


        for (String propApp : PropertiesAppRegister.propAppArray()) {
            //eg.  properties.setProperty("server.port", "${nameapp.server.port}")
            properties.setProperty(propApp, "${%s.%s}".formatted(PropertiesAppRegister.prefix(), propApp));
        }
    
        return properties;

    }



    
}
