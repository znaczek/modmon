package com.wz.modularmonolithexample.payments.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCompleteEvent {

    private String orderId;

    private String paymentReference;
}
