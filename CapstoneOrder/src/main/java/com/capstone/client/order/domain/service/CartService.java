package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.model.dto.response.CartDTO;
import com.capstone.client.order.domain.model.dto.response.CartItemDTO;
import com.capstone.client.order.domain.model.entity.Cart;
import com.capstone.client.order.domain.model.entity.CartItem;
import com.capstone.client.order.domain.repository.CartItemRepo;
import com.capstone.client.order.domain.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private WebClientService webClientService;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    public boolean userExists(Integer userId) {
        return webClientService.userExist(userId);
    }

    @Transactional
    public void clearCart(Integer userId){
        CartDTO cartDTO = getCartByUserId(userId);
        System.out.println("cartDTO.getCartId(): "+cartDTO.getCartId());
        cartItemRepo.deleteAllByCartId(cartDTO.getCartId());
    }

    public CartDTO getCartByUserId(Integer userId) {
        Cart cart = cartRepo.findByUserId(userId);
        List<CartItemDTO> cartItemDTOList = Collections.emptyList();
        if(cart == null || cart.getCartId() == null){
            cart = cartRepo.save(Cart.builder().userId(userId).build());
        } else {
            cartItemDTOList = toCartItemDTOList(cartItemRepo.findByCartId(cart.getCartId()));
        }

        return toCartDTO(cart, cartItemDTOList);
    }

    public CartDTO toCartDTO(Cart cart, List<CartItemDTO> cartItemDTOList){
        return CartDTO.builder().cartId(cart.getCartId()).cartItemDTOList(cartItemDTOList).build();
    }

    public List<CartItemDTO> toCartItemDTOList(List<CartItem> cartItemList){
        if(cartItemList == null || cartItemList.isEmpty()) return Collections.emptyList();

        else return cartItemList.stream().map(this::toCartItemDTO).collect(Collectors.toList());
    }

    public CartItemDTO toCartItemDTO(CartItem cartItem) {
        return CartItemDTO.builder().productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity()).build();
    }

    public boolean productExists(Integer productId) {
        return webClientService.productExist(productId);
    }
}