package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.model.dto.response.CartDTO;
import com.capstone.client.order.domain.model.dto.response.CartItemDTO;
import com.capstone.client.order.domain.model.entity.CartItem;
import com.capstone.client.order.domain.repository.CartItemRepo;
import com.capstone.client.order.domain.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartItemService {

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartService cartService;

    List<CartItem> findByCartId(Integer cartId){
        return this.cartItemRepo.findByCartId(cartId);
    }

    public CartItemDTO update(Integer userId, CartItemDTO cartItemDTO){
        CartDTO cartDTO = cartService.getCartByUserId(userId);
        CartItem updateItem = CartItem.builder()
                .cartId(cartDTO.getCartId())
                .productId(cartItemDTO.getProductId())
                .quantity(cartItemDTO.getQuantity()).build();
        return cartItemRepo.save(updateItem).toDTO();
    }

    public void delete(CartItem updateItem) {
        cartItemRepo.delete(updateItem);
    }

    public boolean isExisted(Integer cartId, Integer productId) {
        return cartItemRepo.existsByCartIdAndProductId(cartId, productId);
    }

    public void deleteAllByCartId(Integer cartId) {
        cartItemRepo.deleteAllByCartId(cartId);
    }

    public CartItemDTO add(Integer userId, CartItemDTO cartItemDTO) {
        CartDTO cartDTO = cartService.getCartByUserId(userId);

        if(isExisted(cartDTO.getCartId(), cartItemDTO.getProductId())){
            CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cartDTO.getCartId(), cartItemDTO.getProductId());
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            return cartItemRepo.save(cartItem).toDTO();
        } else {
            CartItem cartItem = CartItem.builder()
                    .cartId(cartDTO.getCartId())
                    .productId(cartItemDTO.getProductId())
                    .quantity(cartItemDTO.getQuantity())
                    .build();
            return cartItemRepo.save(cartItem).toDTO();
        }
    }

}
