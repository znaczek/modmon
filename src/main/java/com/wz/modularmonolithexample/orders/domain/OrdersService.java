package com.wz.modularmonolithexample.orders.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.wz.modularmonolithexample.dtos.OrderCreateDTO;
import com.wz.modularmonolithexample.dtos.OrderDTO;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final OrderFactory orderFactory;

    public OrderDTO getOrder(String id) {
        return ordersRepository.findById(id)
                               .map(Order::toDTO)
                                .orElseThrow(() -> new RuntimeException(String.format("Order with id %s not found", id)));
    }

    public Page<OrderDTO> getOrders(Pageable pageable) {
        return ordersRepository.findAll(pageable).map(Order::toDTO);
    }


    public void createOrder(OrderCreateDTO orderCreateDTO) {
        orderFactory.create(orderCreateDTO).save();
    }

    public void updateProductName(String productId, String productName) {
        ordersRepository.updateProductNameByProductId(productId, productName);
    }

}
