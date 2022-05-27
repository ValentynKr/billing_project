package com.epam.billing.repository;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private DataSource dataSource;

    public ConnectionManager() {
        try {
            Context initctx = new InitialContext();
            Context envc = (Context) initctx.lookup("java:comp/env");
            dataSource = (DataSource) envc.lookup("jdbc/ST4");
        } catch (NamingException e) {
            System.out.println("Something gone wrong");
        }
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Can`t get connection"); // toDo add my exception instead runtime one.
        }
    }
}
