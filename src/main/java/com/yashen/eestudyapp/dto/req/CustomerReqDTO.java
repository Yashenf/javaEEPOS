package com.yashen.eestudyapp.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerReqDTO {
    private String id;
    private String name;
    private String address;
    private double salary;
}

