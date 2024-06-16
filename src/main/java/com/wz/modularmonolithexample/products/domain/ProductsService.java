package com.wz.modularmonolithexample.products.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.wz.modularmonolithexample.dtos.ProductDTO;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public ProductDTO getProduct(String id) {
        return productsRepository.findById(id)
                                 .map(Product::toDTO)
                                 .orElseThrow(() -> new RuntimeException(String.format("Product with id %s not found", id)));
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productsRepository.findAll(pageable).map(Product::toDTO);
    }

    public void updateProduct(ProductDTO productDTO, String id) {
        var existingProduct = productsRepository.findById(id)
                          .orElseThrow(() -> new RuntimeException(String.format("Product with id %s not found", productDTO.getId())));
        existingProduct.update(productDTO);
    }

}
