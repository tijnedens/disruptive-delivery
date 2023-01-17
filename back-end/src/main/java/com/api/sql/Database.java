package com.api.sql;

import dbConnection.dbConnection;

import java.sql.*;

// TODO: login, where

public class Database {
    Connection conn;
    public String tableName;

    public Database() throws SQLException {
        System.out.println("\nin Database()");
        // check whether db is connected or not
        try {
            this.conn = dbConnection.connect();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!isDBConnected()) {
            System.exit(1);
        }
    }

    private boolean isDBConnected() {
        System.out.println("in isConnected()");
        return this.conn != null;
    }

    public void checkTableExists() {
        // delete table if it exists
        try {
            delTable();
        } catch (Exception ignored) {
            // Do nothing if table doesn't exist
        }
    }

    public String getTableName() { return tableName; }

    private void delTable() throws SQLException {
        System.out.println("in delTable() "+getTableName());

        String delTableSql = "DROP TABLE " + getTableName();
        Statement stmt = this.conn.createStatement();
        stmt.execute(delTableSql);
    }

}
