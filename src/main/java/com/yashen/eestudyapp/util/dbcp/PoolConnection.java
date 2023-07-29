package com.yashen.eestudyapp.util.dbcp;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnection {
    public static Connection getConn(HttpServletRequest req) throws SQLException {
        ServletContext context = req.getServletContext();
        BasicDataSource source= (BasicDataSource) context.getAttribute("dbc");
        Connection conn = source.getConnection();
        return conn;
    }
}
