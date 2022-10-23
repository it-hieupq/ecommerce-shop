package com.capstone.client.order.domain.controller.user;

import com.capstone.client.order.domain.common.ResponseDTO;
import com.capstone.client.order.domain.common.Stringify;
import com.capstone.client.order.domain.model.dto.response.CartDTO;
import com.capstone.client.order.domain.model.dto.response.CartItemDTO;
import com.capstone.client.order.domain.model.entity.Cart;
import com.capstone.client.order.domain.model.entity.CartItem;
import com.capstone.client.order.domain.service.CartItemService;
import com.capstone.client.order.domain.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/users/{userId}/cart")
    public ResponseEntity<Object> getUserCart(@PathVariable(name = "userId") Integer userId){
        String message = "Get cart for user successfully";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        CartDTO cartDTO = null;

        try{
            if(cartService.userExists(userId)) {
                cartDTO = cartService.getCartByUserId(userId);
            } else {
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e){
            e.printStackTrace();
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        map.put("cart", cartDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    /**
     * Update cart item
     * @Param: userId, productId, CartItemDTO
     * */

    @PutMapping("/users/{userId}/cart/cart-item/{productId}")
    public ResponseEntity<Object> updateCartItem(@PathVariable(name = "userId") Integer userId,
                                                 @PathVariable(name = "productId") Integer productId,
                                                 @RequestBody CartItemDTO cartItem){
        String message = "Update cart item successfully";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        CartItemDTO cartItemDTO = null;
        try{
            if(!cartService.userExists(userId)) {
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else if(!cartService.productExists(productId)){
                message = "Product not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                cartItemDTO = cartItemService.update(userId, cartItem);
            }
        } catch (Exception e){
            e.printStackTrace();
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        map.put("cartItem", cartItemDTO);
        return ResponseDTO.getResponse(message, status, map);
    }

    /**
     * @method: Post
     * @param cartItem
     * @param userId
     * description: add new item to cart. if item exist, update new quantity
     * */

    @PostMapping("/users/{userId}/cart/cart-item")
    public ResponseEntity<Object> addCartItem(@PathVariable(name = "userId") Integer userId,
                                                 @RequestBody CartItemDTO cartItem){
        String message = "Add cart item successfully";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        CartItemDTO cartItemDTO = null;
        try{
            if(!cartService.userExists(userId)) {
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else if(!cartService.productExists(cartItem.getProductId())){
                message = "Product not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                cartItemDTO = cartItemService.add(userId, cartItem);
            }
        } catch (Exception e){
            e.printStackTrace();
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        map.put("cartItem", cartItemDTO);
        return ResponseDTO.getResponse(message, status, map);
    }

    @DeleteMapping("/users/{userId}/cart/cart-item/{productId}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable(name = "userId") Integer userId,
                                                 @PathVariable(name = "productId") Integer productId) {
        String message = "Delete cart item successfully";
        HttpStatus status = HttpStatus.OK;

        try{
            if(!cartService.userExists(userId)) {
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else if(!cartService.productExists(productId)) {
                message = "Product not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                CartDTO cartDTO = cartService.getCartByUserId(userId);
                CartItem updateItem = CartItem.builder()
                        .cartId(cartDTO.getCartId())
                        .productId(productId).build();
                cartItemService.delete(updateItem);
            }
        } catch (Exception e){
            e.printStackTrace();
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        return ResponseDTO.getResponse(message, status, Collections.emptyMap());
    }

    @DeleteMapping("/users/{userId}/cart/cart-item")
    public ResponseEntity<Object> deleteAllCartItem(@PathVariable(name = "userId") Integer userId) {

        String message = "Delete all cart item successfully";
        HttpStatus status = HttpStatus.OK;

        try{
            if(!cartService.userExists(userId)) {
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                cartService.clearCart(userId);
            }
        } catch (Exception e){
            e.printStackTrace();
            status = HttpStatus.EXPECTATION_FAILED;
            message = e.getMessage();
        }

        return ResponseDTO.getResponse(message, status, Collections.emptyMap());
    }

}
