package com.zikan.e_shop.service.cart;

import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Cart;
import com.zikan.e_shop.model.CartItem;
import com.zikan.e_shop.repository.CartItemRepository;
import com.zikan.e_shop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.bcel.BcelRenderer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(Long id) {

        Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExcepion("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllCartById(id);
        cart.getItems().clear();
        cartItemRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {

        Cart cart = getCart(id);

        return cart.getItems()
                .stream().map(CartItem :: getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
