package com.yashen.eestudyapp.service.impl;

import com.yashen.eestudyapp.dto.core.CustomerDTO;
import com.yashen.eestudyapp.dto.req.CustomerReqDTO;
import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.entity.Customer;
import com.yashen.eestudyapp.repo.impl.CustomerRepoImpl;
import com.yashen.eestudyapp.repo.repoFactory.RepoFactory;
import com.yashen.eestudyapp.repo.repoFactory.RepoTypes;
import com.yashen.eestudyapp.service.custom.CustomerService;
import com.yashen.eestudyapp.util.mapper.CustomerMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerRepoImpl customerRepo;

    public CustomerServiceImpl() {
        customerRepo= RepoFactory.getRepoFactory().getRepo(RepoTypes.CUSTOMER);
    }

    @Override
    public boolean create(CustomerReqDTO customer, Connection conn) throws SQLException {

        //----------- covert dto to entity
        return customerRepo.create(new Customer(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
        ), conn);
    }

    @Override
    public boolean update(CustomerReqDTO customer, String id, Connection conn) throws SQLException {
        return customerRepo.update(new Customer(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
        ), id, conn);
    }

    @Override
    public CustomerDTO findById(String id, Connection conn) {
        Customer customer = null;
        try {
            customer = customerRepo.findById(id, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (null != customer){
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<CustomerDTO> findAll(Connection conn) throws SQLException {
        ArrayList<Customer> all = customerRepo.findAll(conn);
        ArrayList<CustomerDTO> dtos= new ArrayList<>();
        all.forEach(e->{
            dtos.add(new CustomerDTO(
                    e.getId(),
                    e.getName(),
                    e.getAddress(),
                    e.getSalary()
            ));
        });
        return dtos;
    }

    @Override
    public boolean delete(String id, Connection conn) throws SQLException {
        return customerRepo.delete(id, conn);
    }
}
