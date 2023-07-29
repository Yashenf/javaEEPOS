package com.yashen.eestudyapp.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerRespDTO {
    private String id;
    private String name;
    private String address;
    private double salary;
}
