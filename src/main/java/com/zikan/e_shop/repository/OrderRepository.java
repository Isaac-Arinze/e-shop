package com.zikan.e_shop.repository;

import com.zikan.e_shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}
