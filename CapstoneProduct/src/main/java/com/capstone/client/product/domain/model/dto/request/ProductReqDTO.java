package com.capstone.client.product.domain.model.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ProductReqDTO {
    private Integer productId;
    private String name;
    private Float price;
    private String description;
    private Integer inStock;
    private Boolean active;
    private List<Integer> categoryIdList = new ArrayList<>();
    private Set<ImageReqDTO> images = new HashSet<>();
}
