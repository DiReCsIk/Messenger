package com.nure.ua.application;

import com.nure.ua.application.context.ApplicationContextProvider;
import com.nure.ua.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.nure.ua.util.constant.ServerConstants.*;

@SpringBootApplication(scanBasePackages = SPRING_BOOT_APPLICATION_PATH)
@EnableJpaRepositories(JPA_REPOSITORIES_PATH)
@EntityScan(basePackages = ENTITY_PATH)
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(context);
        Server server = new Server();
        server.start();
    }
}
