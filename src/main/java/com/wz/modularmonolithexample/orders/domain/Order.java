package com.wz.modularmonolithexample.orders.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wz.modularmonolithexample.orders.application.OrderConfirmedEvent;
import com.wz.modularmonolithexample.shared.events.EventDispatcher;
import com.wz.modularmonolithexample.shared.persistence.IdGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders", schema = "orders")
public class Order {

    @Transient
    @Autowired
    private OrdersRepository ordersRepository;

    @Transient
    @Autowired
    private EventDispatcher eventDispatcher;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    @Column(name = "payment_reference")
    private String paymentReference;

    Order(String customerEmail, String productId, String productName, BigDecimal price) {
        this.id = IdGenerator.generateId();
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.status = Status.CREATED;
    }

    void save() {
        ordersRepository.save(this);
    }

    public void confirm() {
        if (status != Status.CREATED) {
            throw new IllegalStateException("Only create order can be confirmed");
        }
        status = Status.CONFIRMED;
        save();
        eventDispatcher.dispatch(new OrderConfirmedEvent(id, price));
        log.info("Order {} confirmed", id);
    }

    @Transactional
    public void complete(String paymentReference) {
        this.paymentReference = paymentReference;
        this.status = Status.COMPLETED;
        save();
        log.info("Order {} completed; payment reference - {}", id, paymentReference);
    }

    private enum Status {
        CREATED,
        CONFIRMED,
        COMPLETED;
    }
}
