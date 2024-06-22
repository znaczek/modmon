package com.wz.modularmonolithexample.orders.application;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {

    String orderId;

    BigDecimal price;

}
