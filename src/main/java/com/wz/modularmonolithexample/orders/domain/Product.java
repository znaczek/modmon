package com.wz.modularmonolithexample.orders.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;

    private String name;

    private BigDecimal price;

}
