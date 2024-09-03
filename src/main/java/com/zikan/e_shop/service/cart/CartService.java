package com.zikan.e_shop.service.cart;

import com.zikan.e_shop.model.Cart;
import com.zikan.e_shop.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart (Long id);
    void clearCart (Long id);
    BigDecimal getTotalPrice (Long id);

//    Long initializeNewCart();

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);


}
