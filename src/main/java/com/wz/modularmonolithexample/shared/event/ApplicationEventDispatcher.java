package com.wz.modularmonolithexample.shared.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationEventDispatcher implements EventDispatcher {

    private final ApplicationEventPublisher eventPublisher;

//    private final OrdersListener ordersListener;
//    private final ProductsRepository productsRepository;

    @Override
    public void dispatch(Object event) {
        eventPublisher.publishEvent(event);
    }

}
