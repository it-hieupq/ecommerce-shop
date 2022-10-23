package com.capstone.client.product.domain.model.dto.request;

import com.capstone.client.product.domain.model.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDTO {
    private Integer categoryId;
    private String name;
    private String description;

    public Category toCategory(){
        Category category = new Category().builder()
                .categoryId(this.categoryId)
                .description(this.description)
                .name(this.name).build();
        return category;
    }
}
