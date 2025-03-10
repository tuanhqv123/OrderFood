package com.orderfood.controller;

import com.orderfood.model.Cart;
import com.orderfood.model.Order;
import com.orderfood.service.CartService;
import com.orderfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/confirm")
    public String showConfirmation(@RequestParam Long tableId, Model model) {
        Cart cart = cartService.getCartByTableId(tableId);
        model.addAttribute("cart", cart);
        return "fragments/order-confirmation :: modal-content";
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam Long tableId, Model model) {
        Order order = orderService.createOrder(tableId);
        model.addAttribute("order", order);
        return "fragments/order-success :: modal-content";
    }

    @GetMapping("/list")
    public String listOrders(@RequestParam(required = false) String status, Model model) {
        model.addAttribute("orders", orderService.getOrdersByStatus(status));
        return "fragments/order-list :: order-content";
    }
}