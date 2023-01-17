package com.api.sql;

import com.api.ApiCommunication;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {

    private Connection connection;

    public DatabaseConnection() {
        connection = connect();
    }

    private Connection connect() {
        String url = "jdbc:sqlite::resource:db/disruptive-delivery.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("SQL: Connected with database");
        } catch (SQLException e) {
            System.out.println("SQL: "+e.getMessage());
        }
        return conn;
    }

    private Map resultSetToJson(ResultSet rs) throws SQLException {
        Map<String, String> result = new HashMap<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            result.put(rs.getMetaData().getColumnLabel(i), rs.getString(i));
        }
        return result;
    }

    public ArrayList<Map> getAllOrders(){
        String sql = "SELECT * FROM orders";
        ArrayList<Map> result = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Map<String, String> json = resultSetToJson(rs);
                result.add(json);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Map getOrder(String id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        Map<String, String> result = new HashMap<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            result = resultSetToJson(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
