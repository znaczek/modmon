package com.wz.modularmonolithexample.orders.infrastructure;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.wz.modularmonolithexample.orders.domain.Product;
import com.wz.modularmonolithexample.orders.domain.ProductsRepository;
import com.wz.modularmonolithexample.products.infrastructure.ProductsController;

@Service
@RequiredArgsConstructor
public class ProductClient implements ProductsRepository {

    private final ProductsController productsController;

    @Override
    public Product getById(String id) {
        return Product.fromDTO(productsController.getProduct(id));
    }
}
