package com.wz.modularmonolithexample.orders.application;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDTO {

    private String id;

    private String productId;

    private String productName;

    private BigDecimal price;

}
