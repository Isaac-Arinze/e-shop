package com.zikan.e_shop.service.order;

import com.zikan.e_shop.dto.OrderDto;
import com.zikan.e_shop.model.Cart;
import com.zikan.e_shop.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder (Long userId);
    OrderDto getOrder (Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    Cart getCartByUserId(Long userId);

    OrderDto convertToDto(Order order);
}
