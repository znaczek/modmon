package com.wz.modularmonolithexample.shared.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationEventDispatcher implements EventDispatcher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void dispatch(Object event) {
        eventPublisher.publishEvent(event);
    }
}
