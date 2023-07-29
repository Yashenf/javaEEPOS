package com.yashen.eestudyapp.api;

import com.yashen.eestudyapp.dto.core.CustomerDTO;
import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.service.impl.CustomerServiceImpl;
import com.yashen.eestudyapp.service.serviceFactory.ServiceFactory;
import com.yashen.eestudyapp.service.serviceFactory.ServiceTypes;
import com.yashen.eestudyapp.util.JsonContext;
import com.yashen.eestudyapp.util.dbcp.PoolConnection;
import com.yashen.eestudyapp.util.resp.StandardResponseEntity;
import jakarta.json.bind.Jsonb;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/api/v1/customers")
public class CustomerController extends HttpServlet {

    private CustomerServiceImpl customerService;
    public CustomerController() {
        customerService= ServiceFactory.getServiceFactory().getService(ServiceTypes.CUSTOMER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Hello get");
        try {
            Connection conn = PoolConnection.getConn(req);
            ArrayList<CustomerDTO> all = customerService.findAll(conn);
            System.out.println("getAll:- "+all);
            String jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    201,
                    "OK",
                    all
            ));
            resp.setContentType("application/json");
            resp.getWriter().print(jsonResp);
        } catch (SQLException e) {
            String jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    501,
                    "Server side error",
                    e.getLocalizedMessage()
            ));
            resp.setContentType("application/json");
            resp.getWriter().print(jsonResp);
        }

    }
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/thogakade",
                    "root",
                    "1234"
            );
            PreparedStatement psmt = conn.prepareStatement("UPDATE customer SET name=? , address=? , salary=? WHERE id=?");
            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            psmt.setString(4,jsonObject.getString("cusId"));
            System.out.println(jsonObject.getString("cusId"));
            psmt.setString(1,jsonObject.getString("cusName"));
            psmt.setString(2,jsonObject.getString("cusAddress"));
            psmt.setDouble(3, Double.parseDouble(jsonObject.getString("salary")));
            System.out.println(Double.parseDouble(jsonObject.getString("salary")));
            int i = psmt.executeUpdate();
            conn.close();
            if (i > 0) {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                builder.add("status",HttpServletResponse.SC_OK);
                builder.add("message","Customer Updated");
                builder.add("data",jsonObject.getString("cusId"));
                resp.getWriter().print(builder.build());
            } else {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                builder.add("status",HttpServletResponse.SC_BAD_GATEWAY);
                builder.add("message","Customer Not Updated");
                builder.add("data","empty");
                resp.getWriter().print(builder.build());
            }

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("status",HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            builder.add("message","Request Failed");
            builder.add("data",e.getLocalizedMessage());
            resp.getWriter().print(builder.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/thogakade",
                    "root",
                    "1234"
            );
            PreparedStatement psmt = conn.prepareStatement("DELETE FROM customer WHERE id=?");
            psmt.setString(1,req.getParameter("cusId"));

            int i = psmt.executeUpdate();
            conn.close();
            if (i > 0) {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                builder.add("status",HttpServletResponse.SC_OK);
                builder.add("message","Customer Deleted");
                builder.add("data","");
                resp.getWriter().print(builder.build());
            } else {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                builder.add("status",HttpServletResponse.SC_BAD_REQUEST);
                builder.add("message","Customer Not Deleted");
                builder.add("data","");
                resp.getWriter().print(builder.build());
            }

        } catch (ClassNotFoundException | SQLException e) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("status",HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            builder.add("message","Request Failed");
            builder.add("data",e.getLocalizedMessage());
            resp.getWriter().print(builder.build());
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("cusId"));
        System.out.println(req.getParameter("cusName"));
        System.out.println(req.getParameter("cusAddress"));
    }


}
