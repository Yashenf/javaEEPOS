package com.yashen.eestudyapp.api;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/items")
public class ItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        BasicDataSource source= (BasicDataSource) context.getAttribute("dbc");
        try {
            Connection conn = source.getConnection();
            PreparedStatement psmt = conn.prepareStatement("Select * from item");
            ResultSet rst = psmt.executeQuery();
            while (rst.next()) {
                System.out.println(rst.getString(1));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
