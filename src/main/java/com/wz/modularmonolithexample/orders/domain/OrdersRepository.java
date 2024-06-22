package com.wz.modularmonolithexample.orders.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, String> {

    @Modifying
    @Query("UPDATE Order o SET o.productName = :name WHERE o.productId = :productId")
    int updateProductNameByProductId(String productId, String name);

}
