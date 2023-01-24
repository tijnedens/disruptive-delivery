package com.api.sql;

import com.email.demo.DemoApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {

    private Connection conn;

    public DatabaseConnection() {
        conn = connect();
    }

    private Connection connect() {
        String url = "jdbc:sqlite::resource:db/ddelivery.sqlite";
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
        String sql = "SELECT * FROM deliveries";
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
        String sql = "SELECT * FROM deliveries WHERE ord_num = ?";
        Map<String, String> result = new HashMap<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = resultSetToJson(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public Map getUser(String name) {
        String sql = "SELECT * FROM users WHERE username = ?";
        Map<String, String> result = new HashMap<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = resultSetToJson(rs);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void createTable() throws SQLException {
        System.out.println("in createTableDeliveries()");
        String createTableSql = "CREATE TABLE deliveries "
                + "( "
                + "ord_num varchar(60) NOT NULL UNIQUE,"
                + "status varchar(60),"
                + "retailer_name varchar(250),"
                + "content varchar(250),"
                + "pickup_loc varchar(250),"
                + "dropoff_loc varchar(250),"
                + "email varchar(250),"
                + "name varchar(60),"
                + "surname varchar(250),"
                + "exp_Date TEXT,"
                + "exp_Time TEXT,"
                + "dim varchar(250)"
                + ");";

        Statement stmt = conn.createStatement();
        stmt.execute(createTableSql);
    }

    public void insertDeliveries(String ordNum, String status, String retailerName,
                                 String content, String pickup, String dropoff, String email) {
        System.out.println("in insertDeliveries("+ordNum+")");
        String insertSql = "INSERT INTO deliveries(ord_num, status, retailer_name, content, " +
                "pickup_loc, dropoff_loc, email) values(?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(insertSql);
            pstmt.setString(1, ordNum);
            pstmt.setString(2, status);
            pstmt.setString(3, retailerName);
            pstmt.setString(4, content);
            pstmt.setString(5, pickup);
            pstmt.setString(6, dropoff);
            pstmt.setString(7, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDeliveries(String ordNum) {
        System.out.println("in deleteDeliveries("+ordNum+")");
        String sql = "DELETE FROM deliveries WHERE ord_num = ?";

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, ordNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // get delivery pick-up and drop-off location
    public void getLoc(String ordNum) throws SQLException {
        System.out.println("in getLoc("+ordNum+")");

        String selectSql = "SELECT ord_num, pickup_loc, dropoff_loc FROM deliveries"; // or specifically: "SELECT fname, lname FROM user"
        Statement stmt =  conn.createStatement(); // sql statement to connect to db
        ResultSet res = stmt.executeQuery(selectSql);

        System.out.println("\n------ deliveries ------");
        while (res.next()) {
            System.out.println(res.getString("ord_num")
                    + ", " + res.getString("pickup_loc")
                    + ", " + res.getString("dropoff_loc"));
        }
    }

    public void getStatus(String ordNum) throws SQLException {
        System.out.println("in getStatus("+ordNum+")");

        String selectSql = "SELECT ord_num, status FROM deliveries WHERE ord_num = '"+ordNum+"'"; // or specifically: "SELECT fname, lname FROM user"
        Statement stmt =  conn.createStatement(); // sql statement to connect to db
        ResultSet res = stmt.executeQuery(selectSql);

        System.out.println("\n------ deliveries ------");
        while (res.next()) {
            System.out.println(res.getString("ord_num")
                    + ", " + res.getString("status"));
        }
    }


    // for the delivery driver
    public void updateStatus(String ordNum, String newStatus) {
        // TODO: call to send email with update status and order details
        System.out.println("in updateStatus("+ordNum+")");
        String updateSql = "UPDATE deliveries SET status = '"+newStatus+"' WHERE ord_num = '"+ordNum+"'"; // or specifically: "SELECT fname, lname FROM user"

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(updateSql);
            pstmt.executeUpdate();
//            if (newStatus.equals("delivered")) {
//                DemoApplication emailDelivered = new DemoApplication();
//            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // for the recipient
    public void updateDeliveryTime(String ordNum, String newTime) {
        String updateSql = "UPDATE deliveries SET exp_Time = '"+newTime+"' WHERE ord_num = '"+ordNum+"'"; // or specifically: "SELECT fname, lname FROM user"

        try {
            PreparedStatement pstmt = this.conn.prepareStatement(updateSql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // show overview of deliveries for the Retailer
    public void loadRetailer(String retailer) throws SQLException {
        System.out.println("in loadRetailer(" + retailer + ")");
        String retailerSql = "SELECT * FROM deliveries WHERE retailer_name = '" + retailer + "'"; // or specifically: "SELECT fname, lname FROM user"

        Statement stmt = conn.createStatement(); // sql statement to connect to db
        ResultSet res = stmt.executeQuery(retailerSql);

        System.out.println("\n------ deliveries ------");
        while (res.next()) {
            System.out.println(res.getString("ord_num")
                    + ", " + res.getString("retailer_name")
                    + ", " + res.getString("content")
                    + ", ");
        }
    }

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

    public void register(String username, String pass, String pos, String fName, String lName, String email) {
        insertUsers(username, pass, pos, fName, lName, email);
    }

    public boolean isLogin(String username, String pass, String pos) {
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
        return false;
    }

    public void delTable() throws SQLException {
        System.out.println("in delTable() deliveries");

        String delTableSql = "DELETE FROM deliveries";
        Statement stmt = this.conn.createStatement();
        stmt.execute(delTableSql);
    }

    // also can be used to REQUEST ORDER DETAILS
    public void loadDB() throws SQLException {
        System.out.println("displaying database");

        String selectSql = "SELECT * FROM deliveries"; // or specifically: "SELECT fname, lname FROM user"
        Statement stmt =  conn.createStatement(); // sql statement to connect to db
        ResultSet res = stmt.executeQuery(selectSql);

        System.out.println("\n------ deliveries ------");
        while (res.next()) {
            System.out.println(res.getString("ord_num")
                    + ", " + res.getString("retailer_name")
                    + ", " + res.getString("pickup_loc")
                    + ", " + res.getString("dropoff_loc")
                    + ", " + res.getString("name")
                    + ", " + res.getString("exp_DateTime")
                    + ", ");
        }
    }
}
