package com.wz.modularmonolithexample.dtos;

import lombok.Data;

@Data
public class OrderCreateDTO {

    private String customerEmail;

    private String productId;

}
