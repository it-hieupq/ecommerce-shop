package com.capstone.client.product.domain.model.entity;

import com.capstone.client.product.domain.model.dto.response.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Integer categoryId;

    @Length(min = 3)
    @NotNull
    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        product.getCategories().add(this);
    }

    public Category(Integer categoryId){
        this.categoryId = categoryId;
    }

    public CategoryDTO toDTO(){
        return CategoryDTO.builder()
                .categoryId(categoryId)
                .name(name)
                .description(description)
                .products(Collections.emptyList())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return categoryId.equals(category.categoryId);
    }

    @Override
    public int hashCode() {
        return categoryId;
    }
}
