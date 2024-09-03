package com.zikan.e_shop.controller;


import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Cart;
import com.zikan.e_shop.model.User;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.cart.CartItemService;
import com.zikan.e_shop.service.cart.CartService;
import com.zikan.e_shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<APIResponse> addItemToCart( // @RequestParam (required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {

        try {
            User user = userService.getUserById(1L);
               Cart cart = cartService.initializeNewCart(user);


            cartItemService.addItemToCart(cart.getId(), productId, quantity);

            return ResponseEntity.ok(new APIResponse("Cart added successfully", null));

        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<APIResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new APIResponse("Item removed successfully", null));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<APIResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Integer quantity) {

        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new APIResponse("cart Updated successfully", null));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));

        }

    }


}
