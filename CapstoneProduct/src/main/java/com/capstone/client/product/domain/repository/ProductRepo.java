package com.capstone.client.product.domain.repository;

import com.capstone.client.product.domain.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Product findByProductId(Integer productId);

    @Query(value = "SELECT * FROM product WHERE active = true order by product.product_id DESC LIMIT :quantity", nativeQuery = true)
    List<Product> getLatestProduct(@Param("quantity") Integer quantity);

    boolean existsByProductIdAndActive(Integer productId, boolean active);

    List<Product> findByProductIdInAndActiveTrue(List<Integer> ids);

    List<Product> findAll(Specification<Product> spec);
}