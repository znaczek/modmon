package com.wz.modularmonolithexample.orders.application;

import lombok.Data;

@Data
public class OrderCreateDTO {

    private String customerEmail;

    private String productId;

}
