package com.wz.modularmonolithexample.dtos;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;
}
