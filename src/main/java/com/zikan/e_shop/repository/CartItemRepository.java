package com.zikan.e_shop.repository;

import com.zikan.e_shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllCartById(Long id);
}
