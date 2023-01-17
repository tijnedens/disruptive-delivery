package com.api.sql;

import com.example.emailsender.DemoApplication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// TODO: insert full info
// TODO: public void updateDateTime{} -recipient
// TODO: return value send text instead of System.out.println when displaying

// TODO: you get email whenever the delivery status
// TODO: you get email when time change

public class DeliveriesDB extends Database {

    public DeliveriesDB() throws SQLException {
        super();
        this.tableName = "deliveries";

        super.checkTableExists();
        createTable();
        loadDB();
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
                    + ", " + res.getString("content")
                    + ", ");
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
            if (newStatus.equals("delivered")) {
                DemoApplication emailDelivered = new DemoApplication();
            }
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

}
