package document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import apicore.environment.PropertiesAppRegister;


@SpringBootApplication
@ComponentScan({"document","apicore"})
public class App {

    public static void main(String[] args) {

        PropertiesAppRegister.prefix("document");
        PropertiesAppRegister.declareAppProps(
            "server.port",
            "spring.data.mongodb.host",
            "spring.data.mongodb.port",
            "spring.data.mongodb.database",
            "spring.data.mongodb.username",
            "spring.data.mongodb.password",
            "spring.data.mongodb.authentication-database"
        );
 
        SpringApplication.run(App.class, args);
    }
}

