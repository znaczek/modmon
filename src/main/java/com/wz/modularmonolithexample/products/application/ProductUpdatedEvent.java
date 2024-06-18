package com.wz.modularmonolithexample.products.application;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class ProductUpdatedEvent {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

}
