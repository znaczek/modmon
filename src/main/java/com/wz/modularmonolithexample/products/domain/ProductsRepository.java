package com.wz.modularmonolithexample.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProductsRepository extends JpaRepository<Product, String> {
}
