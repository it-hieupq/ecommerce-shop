package com.capstone.client.product.domain.model.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {
    private Integer productId;
    private String name;
    private Float price;
    private String description;
    private Integer inStock;
    private Boolean active = true;
    private List<Integer> categoryIdList =  new ArrayList<>();
    private Set<ImageDTO> images = new HashSet<>();
}
