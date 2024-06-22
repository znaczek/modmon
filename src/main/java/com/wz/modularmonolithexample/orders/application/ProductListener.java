package com.wz.modularmonolithexample.orders.application;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.products.application.ProductUpdatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductListener {

    private final OrdersService ordersService;

    @Async
    @EventListener
    public void handleProductUpdated(ProductUpdatedEvent event) {
        log.info("ProductUpdatedEvent captured: {}", event);
        ordersService.updateProductName(event.getId(), event.getName());
    }

}
