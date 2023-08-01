package com.yashen.eestudyapp.repo.custom;

import com.yashen.eestudyapp.entity.Customer;
import com.yashen.eestudyapp.repo.SuperRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerRepo extends SuperRepo {
    boolean create(Customer customer, Connection conn) throws SQLException;
    boolean update(Customer customer, String id,Connection conn) throws SQLException;
    Customer findById(String id,Connection conn) throws SQLException;
    ArrayList<Customer> findAll(Connection conn) throws SQLException;
    boolean delete(String id,Connection conn) throws SQLException;
}
