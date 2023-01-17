package com.api.sql;

import java.sql.*;

public class dbConnection {

    //handles connection to the db
    public static Connection connect() throws ClassNotFoundException, SQLException {
        System.out.println("in getConnection()");
        try {
            Class.forName("org.sqlite.JDBC"); //load jdbc driver
            return DriverManager.getConnection("jdbc:sqlite:ddelivery.sqlite"); // pass the url
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("did NOT open database connection successfully");
        return null;
    }
}



