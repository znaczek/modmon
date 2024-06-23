package com.wz.modularmonolithexample.orders.domain;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wz.modularmonolithexample.orders.application.OrderCreateDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFactory {

    private final AutowireCapableBeanFactory autowirer;

    private final ProductsRepository productsRepository;

    @Transactional
    public Order create(OrderCreateDTO orderCreateDTO) {
        var product = productsRepository.getById(orderCreateDTO.getProductId());
        var order = new Order(orderCreateDTO.getCustomerEmail(), product.getId(), product.getName(), product.getPrice());
        autowirer.autowireBean(order);
        order.save();
        log.info("Order created: id - {}", order.getId());
        return order;
    }

}
