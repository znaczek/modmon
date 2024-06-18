package com.wz.modularmonolithexample.orders.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.wz.modularmonolithexample.shared.persistence.IdGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {

    @Transient
    @Autowired
    private OrdersRepository ordersRepository;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

    Order(String customerEmail, String productId, String productName, BigDecimal price) {
        this.id = IdGenerator.generateId();
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public void save() {
        ordersRepository.save(this);
    }
}
