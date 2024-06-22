package com.wz.modularmonolithexample.orders.application;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.payments.application.PaymentCompleteEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentListener {

    private final OrdersService ordersService;

    @Async
    @EventListener
    public void handlePaymentComplete(PaymentCompleteEvent event) {
        log.info("PaymentCompleteEvent captured: {}", event);
        ordersService.complete(event.getOrderId(), event.getPaymentReference());
    }

}
