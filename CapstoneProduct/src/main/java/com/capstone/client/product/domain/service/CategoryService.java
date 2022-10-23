package com.capstone.client.product.domain.service;

import com.capstone.client.product.domain.model.entity.Category;
import com.capstone.client.product.domain.model.entity.Product;
import com.capstone.client.product.domain.repository.CategoryRepo;
import com.capstone.client.product.domain.model.dto.request.CategoryReqDTO;
import com.capstone.client.product.domain.model.dto.response.CategoryDTO;
import com.capstone.client.product.domain.model.dto.response.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public boolean isExisted(Integer categoryId){
        return categoryRepo.existsById(categoryId);
    }

    public List<CategoryDTO> getAll(Boolean isAdmin){
        List<Category> categories = categoryRepo.findAll();
        return toDTOList(categories, isAdmin);
    }

    public CategoryDTO getCategoryById(Integer categoryId, Boolean isAdmin) {

        Category category = categoryRepo.findByCategoryId(categoryId);

        if (category == null) return null;

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .description(category.getDescription())
                .name(category.getName())
                .build();

        if(isAdmin && category.getProducts()!=null && !category.getProducts().isEmpty()){
            List<ProductDTO> productDTO =
                    category.getProducts()
                            .stream()
                            .map(Product::toDTO)
                            .collect(Collectors.toList());
            categoryDTO.setProducts(productDTO);
        }
        return categoryDTO;
    }

    public List<CategoryDTO> toDTOList(List<Category> categories, boolean isAdmin){
        List<CategoryDTO> DTOList = new ArrayList<>();

        if(categories != null && !categories.isEmpty()){
            categories.forEach( e-> DTOList.add( toDTO(e, isAdmin)));
        }

        return DTOList;
    }

    public CategoryDTO toDTO(Category category, Boolean isAdmin) {

        if(category == null) return null;

        CategoryDTO dto = CategoryDTO.builder()
                .name(category.getName())
                .categoryId(category.getCategoryId())
                .description(category.getDescription())
                .build();

        if(isAdmin && category.getProducts()!=null && !category.getProducts().isEmpty()) {
            List<ProductDTO> productDTOList =
                    category.getProducts().stream()
                            .map(Product::toDTO)
                            .collect(Collectors.toList());
            dto.setProducts(productDTOList);
        }

        return dto;
    }

    public CategoryDTO add(CategoryReqDTO category) {
        Category cat = Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
        Category savedCategory = categoryRepo.save(cat);

        System.out.println(savedCategory);

        return toDTO(savedCategory, false);
    }

    public CategoryDTO update(Integer categoryId, CategoryReqDTO category, Boolean isAdmin) {

        Category cat = Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .categoryId(categoryId)
                .build();

        return toDTO(categoryRepo.save(cat), isAdmin);
    }

    public void delete(Integer categoryId){
        Category category = categoryRepo.findByCategoryId(categoryId);
        category.getProducts().forEach(product -> product.removeCategory(categoryId));
        categoryRepo.delete(category);
    }

    public List<Category> findAllById(List<Integer> categoryIdList) {
        return categoryRepo.findAllById(categoryIdList);
    }
}
