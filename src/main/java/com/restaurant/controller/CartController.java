package com.restaurant.controller;

import com.restaurant.model.Cart;
import com.restaurant.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/sidebar")
    public String getCartSidebar(@RequestParam Long tableId, Model model) {
        Cart cart = cartService.getCart(tableId);
        model.addAttribute("cart", cart);
        model.addAttribute("tableId", tableId);
        return "fragments/cart-sidebar :: cart-content";
    }
}