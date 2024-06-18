package com.wz.modularmonolithexample.products.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import com.wz.modularmonolithexample.products.application.ProductDTO;
import com.wz.modularmonolithexample.products.application.ProductUpdatedEvent;
import com.wz.modularmonolithexample.shared.event.EventDispatcher;

import lombok.Getter;

@Getter
@Entity
@Table(name = "products")
public class Product {

    private final static ModelMapper mapper = new ModelMapper();

    static {
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    @Transient
    @Autowired
    private EventDispatcher eventDispatcher;

    @Getter
    @Transient
    @Autowired
    private ProductsRepository productsRepository;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    public void update(ProductDTO product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        productsRepository.save(this);
        eventDispatcher.dispatch(toUpdatedEvent());
    }

    private ProductUpdatedEvent toUpdatedEvent() {
        var event = new ProductUpdatedEvent();
        mapper.map(this, event);
        return event;
    }

}
