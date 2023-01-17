package com.api.restOutgoing;

import com.api.sql.DatabaseConnection;
import com.google.gson.JsonObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/order/{id}")
    void changeStatus(@PathVariable String id, @RequestParam String newStatus) {
        // Return success code
        databaseConnection.updateStatus(id, newStatus);
    }

    @GetMapping("/user/login")
    boolean login(@RequestParam String username, @RequestParam String password, @RequestParam String pos) {
        return databaseConnection.isLogin(username, password, pos);
    }
}
