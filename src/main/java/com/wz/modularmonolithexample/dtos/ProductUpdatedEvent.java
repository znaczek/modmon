package com.wz.modularmonolithexample.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ProductUpdatedEvent {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

}
