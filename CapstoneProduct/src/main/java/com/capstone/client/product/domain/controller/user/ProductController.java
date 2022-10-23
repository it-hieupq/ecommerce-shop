package com.capstone.client.product.domain.controller.user;

import com.capstone.client.product.domain.common.ResponseDTO;
import com.capstone.client.product.domain.common.Stringify;
import com.capstone.client.product.domain.model.dto.response.ProductDTO;
import com.capstone.client.product.domain.model.entity.filter.ProductFilter;
import com.capstone.client.product.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("UserProductController")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class ProductController {

    private Boolean isAdmin = false;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts() {

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get all products successfully";
        List<ProductDTO> productDTOList = null;

        try {
            productDTOList = productService.getAllProducts(isAdmin);
            map.put("total", productDTOList.size());
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }
        map.put("products", productDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/products/in")
    public ResponseEntity<Object> getProductsIn(@RequestParam(name = "ids", required = false) String ids) {
        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get products in Ids array successfully";
        List<ProductDTO> productDTOList = null;

        try {
            String[] strArray = ids.split(", ");

            List<Integer> productIds = new ArrayList<>();

            for (String s : strArray) {
                if(s!=null && s.length() >0)
                    productIds.add(Integer.parseInt(s));
            }

            if(productIds.isEmpty()){
                productDTOList = Collections.emptyList();
            } else
                productDTOList = productService.getProductByIds(productIds, isAdmin);
        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }
        map.put("products", productDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/products/{productId}")
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
            message = exception.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("product", productDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/products/latest/{quantity}")
    public ResponseEntity<Object> getLatestProduct(@PathVariable(name="quantity", required = false) Integer quantity){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get latest product successfully";
        List<ProductDTO> productDTOList = null;

        if (quantity == null || quantity < 0 ) quantity = 10;

        try {
            productDTOList = productService.getLatestProduct(quantity, isAdmin);
            map.put("total", productDTOList.size());
        } catch (Exception exception){
            exception.printStackTrace();
            message = exception.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("products", productDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @PostMapping("/products/search")
    public ResponseEntity<Object> search(@RequestBody ProductFilter filter){
        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Searching products";
        List<ProductDTO> productDTOList = null;

        try {
            productDTOList = productService.search(filter, isAdmin);
            map.put("total", productDTOList.size());
        } catch (Exception exception){
            exception.printStackTrace();
            message = exception.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("products", productDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }
}
