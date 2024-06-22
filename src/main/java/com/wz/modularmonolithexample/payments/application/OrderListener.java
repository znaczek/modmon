package com.wz.modularmonolithexample.payments.application;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.orders.application.OrderConfirmedEvent;
import com.wz.modularmonolithexample.payments.infrastructure.Payment3PartyClient;
import com.wz.modularmonolithexample.shared.events.EventDispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderListener {

    private final Payment3PartyClient paymentClient;

    private final EventDispatcher eventDispatcher;

    @Async
    @EventListener
    public void handleOrderConfirmation(OrderConfirmedEvent event) {
        log.info("OrderConfirmedEvent captured: {}", event);
        var paymentReference = paymentClient.completePayment(event.getPrice());
        eventDispatcher.dispatch(new PaymentCompleteEvent(event.getOrderId(), paymentReference));
    }

}
