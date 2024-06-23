package com.wz.modularmonolithexample.products.application;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wz.modularmonolithexample.products.domain.Product;
import com.wz.modularmonolithexample.products.domain.ProductsRepository;
import com.wz.modularmonolithexample.shared.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final static ModelMapper mapper = new ModelMapper();

    static {
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    private final ProductsRepository productsRepository;

    public ProductDTO getProduct(String id) {
        return productsRepository.findById(id).map(this::entityToDto).orElseThrow(() -> notFoundException(id));
    }

    private NotFoundException notFoundException(String id) {
        return new NotFoundException(String.format("Product with id %s not found", id));
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productsRepository.findAll(pageable).map(this::entityToDto);
    }

    @Transactional
    public void updateProduct(ProductDTO productDTO, String id) {
        var existingProduct = productsRepository.findById(id)
                                                .orElseThrow(() -> new NotFoundException(
                                                        String.format("Product with id %s not found", productDTO.getId())));
        existingProduct.update(productDTO);
    }

    private ProductDTO entityToDto(Product product) {
        var dto = new ProductDTO();
        mapper.map(product, dto);
        return dto;
    }

}
