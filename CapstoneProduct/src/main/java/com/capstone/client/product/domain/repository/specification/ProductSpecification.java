package com.capstone.client.product.domain.repository.specification;

import com.capstone.client.product.domain.model.entity.Category;
import com.capstone.client.product.domain.model.entity.filter.ProductFilter;
import com.capstone.client.product.domain.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class ProductSpecification {
    public static Specification<Product> search(ProductFilter filter) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getKeyword() + "%"));
            }

            if (filter.getMinPrice() != null && filter.getMinPrice() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null && filter.getMaxPrice() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            List<Predicate> predicatesList = new ArrayList<>();

            if(filter.getCategoryIdList()!=null && !filter.getCategoryIdList().isEmpty()){
                Join<Product, Category> productCategoryJoin = root.join("categories"); // join object

                System.out.println(productCategoryJoin.get("categoryId").toString());

                filter.getCategoryIdList().forEach(e -> {
                    predicatesList.add(criteriaBuilder.equal(productCategoryJoin.get("categoryId"), e));
                });
            }

            Predicate orPredicate = null;
            if(!predicatesList.isEmpty()) {
                orPredicate = criteriaBuilder.or(predicatesList.toArray(new Predicate[]{}));
            }
            if(orPredicate != null) {
                predicates.add(orPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

}
