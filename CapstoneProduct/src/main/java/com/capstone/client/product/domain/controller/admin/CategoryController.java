package com.capstone.client.product.domain.controller.admin;

import com.capstone.client.product.domain.common.ResponseDTO;
import com.capstone.client.product.domain.common.Stringify;
import com.capstone.client.product.domain.model.dto.request.CategoryReqDTO;
import com.capstone.client.product.domain.model.dto.response.CategoryDTO;
import com.capstone.client.product.domain.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("AdminCategories")
@RequestMapping("/admin/categories")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class CategoryController {

    private Boolean isAdmin = true;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "";
        List<CategoryDTO> categoryDTOList = null;

        try {
            categoryDTOList = categoryService.getAll(isAdmin);
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("categories", categoryDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getById(@PathVariable(name = "categoryId") Integer categoryId){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        String message = "Get product successfully";
        CategoryDTO categoryDTO = null;
        try {
            if(categoryService.isExisted(categoryId))
                categoryDTO = categoryService.getCategoryById(categoryId ,isAdmin);
            else message = "Category not found";

        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("category", categoryDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    @PostMapping("")
    public ResponseEntity<Object> add(@RequestBody CategoryReqDTO category){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Product has been added";
        CategoryDTO categoryDTO = null;

        try {
            categoryDTO = categoryService.add(category);
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("category", categoryDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> update(@PathVariable(name="categoryId") Integer categoryId, @RequestBody CategoryReqDTO category){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Updated category";
        CategoryDTO categoryDTO = null;
        try {
            if(categoryService.isExisted(categoryId)){
                categoryDTO = categoryService.update(categoryId, category, isAdmin);
                map.put("category", categoryDTO);
            } else message = "Category not found";

        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("category", categoryDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> delete(@PathVariable(name="categoryId") Integer categoryId){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Deleted category";

        try {

            if(categoryService.isExisted(categoryId))
                categoryService.delete(categoryId);
            else message = "Category not found";

        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        return ResponseDTO.getResponse(message, status, map);
    }
}
