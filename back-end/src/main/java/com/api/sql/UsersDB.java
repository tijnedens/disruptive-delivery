package com.api.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDB extends Database {

    // TODO: customer support gets customer details

    public UsersDB() throws SQLException {
        super();
        this.tableName = "users";

        super.checkTableExists();
        createTable();
        loadDB();
    }

    private void createTable() throws SQLException {
        System.out.println("in createTableUsers()");
        String createTableSql = "CREATE TABLE users "
                + "( "
                + "username varchar(250) NOT NULL UNIQUE,"
                + "password varchar(250) NOT NULL,"
                + "position varchar(250) NOT NULL,"
                + "name varchar(60),"
                + "surname varchar(60),"
                + "email varchar(250)"
                + " );";

        Statement stmt = conn.createStatement();
        stmt.execute(createTableSql);
    }

    /*
    public void insertUsers(String username, String pass, String pos) {
        System.out.println("in insertUsers()");
        String insertSql = "INSERT INTO users(username, password, position) values(?,?,?)";

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(insertSql);
            pstmt.setString(1, username);
            pstmt.setString(2, pass);
            pstmt.setString(3, pos);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    */

    public void insertUsers(String username, String pass, String pos, String fName, String lName, String email) { //also used for registering/sign up
        System.out.println("in insertUsers("+fName+")");
        String insertSql = "INSERT INTO users(username, password, position, name, surname, email) values(?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(insertSql);
            pstmt.setString(1, username); // check from query where username == user
            pstmt.setString(2, pass);
            pstmt.setString(3, pos);
            pstmt.setString(4, fName);
            pstmt.setString(5, lName);
            pstmt.setString(6, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUsers(String username) {
        System.out.println("in deleteUsers("+username+")");
        String sql = "DELETE FROM users WHERE username = ?";

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadDB() throws SQLException {
        System.out.println("displaying database");

        String selectSql = "SELECT * FROM users"; // or specifically: "SELECT fname, lname FROM user"
        Statement stmt =  conn.createStatement(); // sql statement to connect to db
        ResultSet res = stmt.executeQuery(selectSql);

        System.out.println("\n------ users ------");
        while (res.next()) {
            System.out.println(res.getString("name")
                    + ", " + res.getString("surname")
                    + ", " + res.getString("username")
                    + ", " + res.getString("password")
                    + ", ");
        }
    }

    public void register(String username, String pass, String pos, String fName, String lName, String email) {
        insertUsers(username, pass, pos, fName, lName, email);
    }

    public boolean isLogin(String username, String pass, String pos) throws SQLException {
        System.out.println("in isLogin("+username+")");
        Statement stmt = null;
        ResultSet res = null;

        String loginSql = "SELECT * FROM users WHERE username = '"+username+"' and password = '"+pass+"' and position = '"+pos+"'";

        try {
            stmt =  conn.createStatement(); // sql statement to connect to db
            res = stmt.executeQuery(loginSql);
            // check if user can login
            if (res.next()) {
                System.out.println("--------------> can login");
                return true;
            }
            System.out.println("-------------> canNOT login");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally { //close connections
            stmt.close();
            res.close();
        }
        return false;
    }

}
