package com.zikan.e_shop.service.cart;

import com.zikan.e_shop.model.CartItem;

public interface CartItemService {

    void addItemToCart (Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity (Long cartId, Long productId, int quantity);

    //using helper methos
    CartItem getCartItem(Long cartId, Long productId);
}
