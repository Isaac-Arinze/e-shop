package com.zikan.e_shop.controller;


import com.zikan.e_shop.dto.OrderDto;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Order;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<APIResponse> createOrder(@RequestParam Long orderId) {

        try {
            Order order = orderService.placeOrder(orderId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new APIResponse("Item order success", orderDto));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("User not found", order));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Oops!", e.getMessage()));
        }

    }

    @GetMapping("{userId}/orders")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId) {
        try {

            List<OrderDto> userOrder = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("Item order success", userOrder));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse("Error occured", e.getMessage()));

        }


    }
}
