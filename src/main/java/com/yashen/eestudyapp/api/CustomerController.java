package com.yashen.eestudyapp.api;

import com.yashen.eestudyapp.dto.core.CustomerDTO;
import com.yashen.eestudyapp.dto.req.CustomerReqDTO;
import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.service.impl.CustomerServiceImpl;
import com.yashen.eestudyapp.service.serviceFactory.ServiceFactory;
import com.yashen.eestudyapp.service.serviceFactory.ServiceTypes;
import com.yashen.eestudyapp.util.JsonContext;
import com.yashen.eestudyapp.util.dbcp.PoolConnection;
import com.yashen.eestudyapp.util.resp.StandardResponseEntity;
import jakarta.json.bind.Jsonb;
import lombok.SneakyThrows;
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
        customerService = ServiceFactory.getServiceFactory().getService(ServiceTypes.CUSTOMER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Connection conn = null;
        try {
            conn = PoolConnection.getConn(req);
        } catch (SQLException e) {
            String jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    501,
                    "Connection Not Provide!",
                    null
            ));
            resp.setContentType("application/json");
            resp.getWriter().print(jsonResp);
        }
        if (action != null) {
            if (action.equals("getAll")) {
                try {
                    ArrayList<CustomerDTO> all = customerService.findAll(conn);
                    System.out.println("getAll:- " + all);
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
            } else if (action.equals("findOne")) {
                String id = req.getParameter("id");
                System.out.println("Id in Api layer is :- " + id);
                CustomerDTO cusById = customerService.findById(id, conn);
                StandardResponseEntity rspEntity;
                System.out.println("In Api layer :- " + cusById);
                if (null != cusById) {
                    rspEntity = new StandardResponseEntity(
                            201,
                            "OK",
                            cusById
                    );
                } else {
                    rspEntity = new StandardResponseEntity(
                            201,
                            "OK",
                            "No Data Available"
                    );
                }
                String jsonResp = JsonContext.getJsonb().toJson(rspEntity);
                resp.setContentType("application/json");
                resp.getWriter().print(jsonResp);
            } else {
                String jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                        403,
                        "Bad Request. wrong action type (getAll OR findOne)",
                        null
                ));
                resp.setContentType("application/json");
                resp.getWriter().print(jsonResp);
            }

        } else {
            String jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    501,
                    "Server side error",
                    null
            ));
            resp.setContentType("application/json");
            resp.getWriter().print(jsonResp);
        }

        resp.setStatus(201);
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = PoolConnection.getConn(req);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = req.getReader().readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = stringBuilder.toString();
        Jsonb jsonb = JsonContext.getJsonb();
        CustomerReqDTO dto = jsonb.fromJson(jsonString, CustomerReqDTO.class);
        System.out.println("DTO is :-" + dto);
        boolean b = customerService.create(dto, conn);
        String jsonResp;
        if (b) {
            jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    201,
                    "Customer Saved",
                    dto.getId()
            ));

        }else {
            jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    403,
                    "Bad Request. Not saved",
                    null
            ));
        }
        resp.setContentType("application/json");
        resp.getWriter().print(jsonResp);
    }



    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Connection conn = PoolConnection.getConn(req);
        //Get DTO
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
            while ((line = req.getReader().readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = stringBuilder.toString();
        Jsonb jsonb = JsonContext.getJsonb();
        CustomerReqDTO dto = jsonb.fromJson(jsonString, CustomerReqDTO.class);

        //Get ID
        String id = req.getParameter("id");

        boolean flag = customerService.update(dto, id, conn);

        String jsonResp;
        if (flag){
            jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    201,
                    "Updated",
                    dto
            ));
        }else {
            jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    403,
                    "Bad Request. Not Updated",
                    id
            ));
        }

        resp.getWriter().print(jsonResp);
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = PoolConnection.getConn(req);
        resp.setContentType("application/json");

        //get customer id
        String id = req.getParameter("id");

        boolean flag = customerService.delete(id, conn);

        String jsonResp;
        if (flag){
            jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    201,
                    "Deleted",
                    id
            ));
        }else {
            jsonResp = JsonContext.getJsonb().toJson(new StandardResponseEntity(
                    403,
                    "Bad Request. Not Deleted",
                    id
            ));
        }

        resp.getWriter().print(jsonResp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


}
