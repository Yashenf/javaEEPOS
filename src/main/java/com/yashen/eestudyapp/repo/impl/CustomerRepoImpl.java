package com.yashen.eestudyapp.repo.impl;

import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.entity.Customer;
import com.yashen.eestudyapp.repo.custom.CustomerRepo;
import com.yashen.eestudyapp.util.mapper.CustomerMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {

    @Override
    public boolean create(Customer customer, Connection conn) throws SQLException {
        PreparedStatement psmt = conn.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
        psmt.setString(1,customer.getId());
        psmt.setString(2,customer.getName());
        psmt.setString(3, customer.getAddress());
        psmt.setDouble(4,customer.getSalary());
        if (psmt.executeUpdate() > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Customer customer, String id, Connection conn) throws SQLException {
        PreparedStatement psmt = conn.prepareStatement("UPDATE customer SET name=? , address=? , salary=? WHERE id=?");
        psmt.setString(4,id);
        psmt.setString(1,customer.getName());
        psmt.setString(2, customer.getAddress());
        psmt.setDouble(3, customer.getSalary());
        if (psmt.executeUpdate() > 0){
            System.out.println("Updated----------------");
            return true;
        }else {
            System.out.println("Not - Updated----------------");
            return false;
        }
    }

    @Override
    public Customer findById(String id, Connection conn) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM customer WHERE id=?");
        pstm.setString(1,id);
        System.out.println("ID is :-"+id);
        ResultSet rst = pstm.executeQuery();
        Customer selectedCustomer= new Customer();
        while (rst.next()) {
            System.out.println(rst.toString());
            selectedCustomer.setId(rst.getString(1));
            selectedCustomer.setName(rst.getString(2));
            selectedCustomer.setAddress(rst.getString(3));
            selectedCustomer.setSalary(rst.getDouble(4));
        }
        System.out.println("In Repo layer :- "+selectedCustomer);
        return selectedCustomer;
    }

    @Override
    public ArrayList<Customer> findAll(Connection conn) throws SQLException {
        PreparedStatement psmt = conn.prepareStatement("SELECT * FROM CUSTOMER");
        ResultSet resultSet = psmt.executeQuery();
        List<Customer> customers= new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            double salary = resultSet.getDouble(4);
            customers.add(new Customer(
                    id,
                    name,
                    address,
                    salary
            ));
            System.out.println(id+","+name+","+address+","+salary);
        }
        System.out.println("repo list:-"+customers);
        return (ArrayList<Customer>) customers;
    }

    @Override
    public boolean delete(String id, Connection conn) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement("DELETE FROM customer WHERE id=?");
        pstm.setString(1,id);
        if (pstm.executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }
}
