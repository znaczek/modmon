package com.wz.modularmonolithexample.orders.infrastructure;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.dtos.ProductUpdatedEvent;
import com.wz.modularmonolithexample.orders.domain.OrdersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersListener {

    private final OrdersService ordersService;

    @EventListener
    public void handleProductUpdated(ProductUpdatedEvent event) {
        log.info("Event captured {}", event);
        ordersService.updateProductName(event.getId(), event.getName());
    }

}
