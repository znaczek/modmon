package com.wz.modularmonolithexample.orders.infrastructure;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

import com.wz.modularmonolithexample.orders.domain.Product;
import com.wz.modularmonolithexample.orders.domain.ProductsRepository;
import com.wz.modularmonolithexample.products.application.ProductDTO;
import com.wz.modularmonolithexample.products.application.ProductsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductClient implements ProductsRepository {

    private static final ModelMapper mapper = new ModelMapper();

    static {
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private final ProductsService productsService;

    @Override
    public Product getById(String id) {
        return dtoToEntity(productsService.getProduct(id));
    }

    private Product dtoToEntity(ProductDTO dto) {
        var product = new Product();
        mapper.map(dto, product);
        return product;
    }
}
