package com.api.restOutgoing;

import com.api.sql.DatabaseConnection;
import com.google.gson.JsonObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@SpringBootApplication
@RestController
public class RestApiController {

    private DatabaseConnection databaseConnection;

    public RestApiController() {
        this.databaseConnection = new DatabaseConnection();
    }

    @GetMapping("/order/{id}")
    Map getOrderById(@PathVariable String id) {
        return databaseConnection.getOrder(id);
    }
}
