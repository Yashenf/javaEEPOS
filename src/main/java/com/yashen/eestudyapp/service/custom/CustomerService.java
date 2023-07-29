package com.yashen.eestudyapp.service.custom;

import com.yashen.eestudyapp.dto.core.CustomerDTO;
import com.yashen.eestudyapp.dto.req.CustomerReqDTO;
import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.service.SuperService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerService extends SuperService {
    boolean create(CustomerReqDTO customer, Connection conn);
    boolean update(CustomerReqDTO customer, String id,Connection conn);
    CustomerDTO findById(String id, Connection conn);
    ArrayList<CustomerDTO> findAll(Connection conn) throws SQLException;
    boolean delete(String id,Connection conn);
}
