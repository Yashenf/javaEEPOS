package com.yashen.eestudyapp.util.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StandardResponseEntity {
    private int status;
    private String message;
    private Object data;
}
