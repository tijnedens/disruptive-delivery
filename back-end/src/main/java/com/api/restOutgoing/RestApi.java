package com.api.restOutgoing;

import com.api.sql.DatabaseConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RestApi {
    public static void main(String[] args) {
        SpringApplication.run(RestApiController.class, args);
    }
}
