package com.capstone.client.product.domain.repository;

import com.capstone.client.product.domain.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findByCategoryId(Integer categoryId);
}
