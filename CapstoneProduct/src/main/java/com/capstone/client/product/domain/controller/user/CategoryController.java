package com.capstone.client.product.domain.controller.user;

import com.capstone.client.product.domain.common.ResponseDTO;
import com.capstone.client.product.domain.common.Stringify;
import com.capstone.client.product.domain.model.dto.response.CategoryDTO;
import com.capstone.client.product.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("UserCategoryController")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class CategoryController {

    private Boolean isAdmin = false;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Object> getAll() {

        List<CategoryDTO> categoryDtoList = null;
        String message = "Get category successfully";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();

        try {
            categoryDtoList = categoryService.getAll(isAdmin);
        } catch (Exception exception){
            status = HttpStatus.EXPECTATION_FAILED;
            message =exception.getMessage();
        }

        map.put("categories", categoryDtoList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable(name="categoryId") Integer categoryId){

        CategoryDTO categoryDto = null;
        String message = "Get category successfully";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();

        try {
            if(categoryService.isExisted(categoryId))
                categoryDto = categoryService.getCategoryById(categoryId, true);
            else {
                status = HttpStatus.NOT_FOUND;
                message = "Category not found";
            }
        } catch (Exception exception){
            status = HttpStatus.EXPECTATION_FAILED;
            message =exception.getMessage();
        }

        map.put("category", categoryDto);

        return ResponseDTO.getResponse(message, status, map);
    }
}
