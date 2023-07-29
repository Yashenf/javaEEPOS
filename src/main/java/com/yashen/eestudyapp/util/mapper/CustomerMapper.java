package com.yashen.eestudyapp.util.mapper;

import com.yashen.eestudyapp.dto.core.CustomerDTO;
import com.yashen.eestudyapp.dto.req.CustomerReqDTO;
import com.yashen.eestudyapp.dto.resp.CustomerRespDTO;
import com.yashen.eestudyapp.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CustomerMapper {
    CustomerMapper CUSTOMER_MAPPER= Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDto(Customer customer);
    Customer toEntity(CustomerDTO customerDTO);

    CustomerDTO toCoreDto (CustomerReqDTO customerReqDTO);
    CustomerRespDTO fromCoreDto (CustomerDTO customerDTO);

    List<CustomerDTO> toCustomerDTOs(List<Customer> customers);

}
