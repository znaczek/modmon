package com.wz.modularmonolithexample.orders.domain;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.wz.modularmonolithexample.dtos.OrderCreateDTO;

@Service
@RequiredArgsConstructor
public class OrderFactory {

    private final AutowireCapableBeanFactory autowirer;

    private final ProductsRepository productsRepository;

    public Order create(OrderCreateDTO orderCreateDTO) {
        var product = productsRepository.getById(orderCreateDTO.getProductId());
        var order = new Order(orderCreateDTO.getCustomerEmail(),
                              product.getId(),
                              product.getName(),
                              product.getPrice());
        autowirer.autowireBean(order);
        return order;
    }

}
