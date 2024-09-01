package com.zikan.e_shop.controller;

import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Cart;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{cartId}/cart")
    public ResponseEntity<APIResponse> getCart (@PathVariable Long cartId) {
        try {

            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new APIResponse("Suceess", cart));
        }
        catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<APIResponse> clearCart (@PathVariable Long cartId){
        try{
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new APIResponse("Cart successfully cleared", null));
    } catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/{cartId/cart/total-price}")
    public ResponseEntity<APIResponse> getTotalAmount (@PathVariable Long cartId){

        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new APIResponse("Total price", totalPrice));
        }
        catch (ResourceNotFoundExcepion e){
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }
}
