package com.example.shop.respository;

import com.example.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT u FROM Product u WHERE u.priceSale != 0")
    List<Product> findProductSale ();
    @Query("SELECT u FROM Product u WHERE u.category.id = :categoryId")
    List<Product> findProductsByCategoryId (@Param("categoryId") Long categoryId);

}
