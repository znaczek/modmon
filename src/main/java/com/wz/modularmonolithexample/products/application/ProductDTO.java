package com.wz.modularmonolithexample.products.application;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;
}
