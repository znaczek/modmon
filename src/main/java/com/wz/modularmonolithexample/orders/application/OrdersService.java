package com.wz.modularmonolithexample.orders.application;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wz.modularmonolithexample.orders.domain.Order;
import com.wz.modularmonolithexample.orders.domain.OrderFactory;
import com.wz.modularmonolithexample.orders.domain.OrdersRepository;
import com.wz.modularmonolithexample.shared.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private static final ModelMapper mapper = new ModelMapper();

    private final OrdersRepository ordersRepository;

    private final OrderFactory orderFactory;

    public OrderDTO getOrder(String id) {
        return entityToDto(get(id));
    }

    public Page<OrderDTO> getOrders(Pageable pageable) {
        return ordersRepository.findAll(pageable).map(this::entityToDto);
    }

    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) {
        return entityToDto(orderFactory.create(orderCreateDTO));
    }

    @Transactional
    public void updateProductName(String productId, String productName) {
        ordersRepository.updateProductNameByProductId(productId, productName);
    }

    @Transactional
    public void confirm(String orderId) {
        get(orderId).confirm();
    }

    public void complete(String orderId, String paymentReference) {
        get(orderId).complete(paymentReference);
    }

    private OrderDTO entityToDto(Order order) {
        var dto = new OrderDTO();
        mapper.map(order, dto);
        return dto;
    }

    private Order get(String id) {
        return ordersRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Order with id %s not found", id)));
    }

}
