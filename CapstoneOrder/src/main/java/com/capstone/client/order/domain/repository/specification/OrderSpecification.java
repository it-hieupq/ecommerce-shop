package com.capstone.client.order.domain.repository.specification;

import com.capstone.client.order.domain.model.entity.OrderDetail;
import com.capstone.client.order.domain.model.entity.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


public class OrderSpecification {
    public static Specification<OrderDetail> search(OrderFilter filter) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            if(filter.getUserIdList()!=null && !filter.getUserIdList().isEmpty()){
                filter.getUserIdList().forEach(id-> {
                    predicates.add(criteriaBuilder.equal(root.get("userId"), id));
                });
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

}
