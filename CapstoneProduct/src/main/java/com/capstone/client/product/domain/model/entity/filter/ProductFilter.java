package com.capstone.client.product.domain.model.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductFilter {
    private String keyword; // lIKE
    private Float minPrice; // equal or greater than
    private Float maxPrice; // equal or less than
    private List<Integer> categoryIdList; // in
}
