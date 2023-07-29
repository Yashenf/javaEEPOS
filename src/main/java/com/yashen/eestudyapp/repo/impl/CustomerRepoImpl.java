package com.yashen.eestudyapp.repo.impl;

import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.entity.Customer;
import com.yashen.eestudyapp.repo.custom.CustomerRepo;
import com.yashen.eestudyapp.util.mapper.CustomerMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements CustomerRepo {

    @Override
    public boolean create(Customer customer, Connection conn) {
        return false;
    }

    @Override
    public boolean update(Customer customer, String id, Connection conn) {
        return false;
    }

    @Override
    public Customer findById(String id, Connection conn) {
        return null;
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
    public boolean delete(String id, Connection conn) {
        return false;
    }
}
