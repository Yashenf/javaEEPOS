package com.yashen.eestudyapp.util.config;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ConfigServletContext implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/thogakade");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        dataSource.setMaxTotal(5);
        dataSource.setInitialSize(4);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbc",dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        BasicDataSource basicDataSource = (BasicDataSource) servletContext.getAttribute("dbc");
        try {
            basicDataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
