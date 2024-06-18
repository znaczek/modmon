package com.wz.modularmonolithexample.products.presentation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wz.modularmonolithexample.products.application.ProductDTO;
import com.wz.modularmonolithexample.products.application.ProductsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(Pageable pageable) {
        return toResponseEntity(productsService.getProducts(pageable));
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable String id) {
        return productsService.getProduct(id);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable String id, @RequestBody ProductDTO product) {
        productsService.updateProduct(product, id);
    }

    private <T> ResponseEntity<List<T>> toResponseEntity(Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total", String.valueOf(page.getTotalElements()));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
