package com.capstone.client.product.domain.controller.admin;

import com.capstone.client.product.domain.common.ResponseDTO;
import com.capstone.client.product.domain.common.Stringify;
import com.capstone.client.product.domain.helper.ImportFileHelper;
import com.capstone.client.product.domain.model.dto.request.ProductReqDTO;
import com.capstone.client.product.domain.model.dto.response.ProductDTO;
import com.capstone.client.product.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController("AdminProductController")
@RequestMapping("/admin/products")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class ProductController {

    private Boolean isAdmin = true;
    @Autowired
    private ProductService productService;

    // Get all product
    @GetMapping("")
    public ResponseEntity<Object> getAllProducts(){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get all products successfully";
        List<ProductDTO> productDTOList = null;

        try {
            productDTOList = productService.getAllProducts(isAdmin);
        } catch (Exception e){
            map.put("error", e.getMessage());
        }
        map.put("products", productDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProductById(@PathVariable(name="productId") Integer productId) {
        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get product successfully";
        ProductDTO productDTO = null;

        try {
            if (productService.isExisted(productId))
                productDTO = productService.getProductById(productId, isAdmin);
            else {
                message = "Product not found";
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception exception){
            message =  exception.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("product", productDTO);
        return ResponseDTO.getResponse(message, status, map);
    }

//    Add new product
    @PostMapping("")
    public ResponseEntity<Object> add(@RequestBody ProductReqDTO productReqDTO){
        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        String message = "Product has been added";
        ProductDTO productDTO = null;
        try {
            productDTO = productService.add(productReqDTO);
        } catch (Exception e){
            e.printStackTrace();
            message =  e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }
        map.put("product", productDTO);

        return ResponseDTO.getResponse(message ,status, map);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Object> update(@PathVariable(name = "productId") Integer productId, @RequestBody ProductReqDTO productReqDTO){
        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        String message = "Product has been updated";

        System.out.println("productReqDTO size: "+productReqDTO.getImages().size());

        ProductDTO productDTO = null;

        try {
            if(productService.isExisted(productId)){
                productDTO = productService.update(productReqDTO);
            } else {
                message = "Product not found";
            }
        } catch (Exception e){
            e.printStackTrace();
            message =  e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("product", productDTO);

        return ResponseDTO.getResponse(message ,status, map);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteById(@PathVariable("productId") int productId) {

        String message = "Delete product successfully";
        HttpStatus status = HttpStatus.OK;

        try {
            if(productService.isExisted(productId)){
                productService.delete(productId);
            } else {
                message ="Product not found";
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            message = e.getLocalizedMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        return ResponseDTO.getResponse(message, status, Collections.emptyMap());
    }

    @PostMapping("/import")
    public ResponseEntity<Object> importProduct(@RequestParam("file") MultipartFile file) {

        String message = "Products has been import successfully from file: " + file.getOriginalFilename();

        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();

        List<ProductDTO> importedProduct = new ArrayList<>();

        try{
            if (productService.isValidExcelOrCsvFormat(file)) {
                importedProduct = productService.importProductData(file);
            } else {
                message = "The file: " + file.getOriginalFilename() + " has not excel/csv format";
                status = HttpStatus.EXPECTATION_FAILED;
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("products", importedProduct);
        map.put("size", importedProduct.size());

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/exists/{productId}")
    public ResponseEntity<Object> existsById(@PathVariable(name = "productId") Integer productId){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        boolean result = false;

        try {
            result = productService.isExisted(productId);
        } catch (Exception e){
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }

        map.put("result", result);

        return ResponseEntity.status(status).body(map);
    }

}
