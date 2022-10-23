package com.capstone.client.product.domain.model.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    private Integer categoryId;
    private String name;
    private String description;
    private List<ProductDTO> products = new ArrayList<>();
}
