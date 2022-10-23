package com.capstone.client.product.domain.model.entity;

import com.capstone.client.product.domain.model.dto.response.ProductDTO;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name="product_id")
    private Integer productId;
    @NotNull
    private String name;
    @NotNull
    private Float price;
    private String description;
    @NotNull
    private Integer inStock;
    @NotNull
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    Set<Image> images = new HashSet<>();

    public void removeCategory(Integer categoryId){
        categories.removeIf(category -> category != null && category.getCategoryId().equals(categoryId));
    }

    public void removeImage(Image image){
        images.removeIf(i -> image.getImageId().equals(i.getImageId()));
    }

    public ProductDTO toDTO() {
        return ProductDTO.builder()
                .productId(productId)
                .active(active)
                .inStock(inStock)
                .price(price)
                .description(description)
                .name(name)
                .categoryIdList(this.categories.stream().map(Category::getCategoryId).collect(Collectors.toList()))
                .images(this.images.stream().map(Image::toDTO).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public int hashCode() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return productId.equals(product.productId);
    }
}