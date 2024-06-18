package com.wz.modularmonolithexample.orders.application;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.orders.domain.Order;
import com.wz.modularmonolithexample.orders.domain.OrderFactory;
import com.wz.modularmonolithexample.orders.domain.OrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private static final ModelMapper mapper = new ModelMapper();

    private final OrdersRepository ordersRepository;

    private final OrderFactory orderFactory;

    public OrderDTO getOrder(String id) {
        return ordersRepository.findById(id)
                               .map(this::entityToDto)
                               .orElseThrow(() -> new RuntimeException(String.format("Order with id %s not found", id)));
    }

    public Page<OrderDTO> getOrders(Pageable pageable) {
        return ordersRepository.findAll(pageable).map(this::entityToDto);
    }

    public void createOrder(OrderCreateDTO orderCreateDTO) {
        orderFactory.create(orderCreateDTO).save();
    }

    public void updateProductName(String productId, String productName) {
        ordersRepository.updateProductNameByProductId(productId, productName);
    }

    private OrderDTO entityToDto(Order order) {
        var dto = new OrderDTO();
        mapper.map(order, dto);
        return dto;
    }

}
