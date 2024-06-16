package com.wz.modularmonolithexample.orders.domain;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import com.wz.modularmonolithexample.dtos.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private String id;

    private String name;

    private BigDecimal price;

    public static Product fromDTO(ProductDTO dto) {
        var product = new Product();
        mapper.map(dto, product);
        return product;
    }

}
