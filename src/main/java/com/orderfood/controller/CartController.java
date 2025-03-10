package com.orderfood.controller;

import com.orderfood.model.Cart;
import com.orderfood.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/view")
    public String viewCart(@RequestParam Long tableId, Model model) {
        Cart cart = cartService.getCartByTableId(tableId);
        model.addAttribute("cart", cart);
        return "fragments/cart-sidebar :: cart-content";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long tableId,
            @RequestParam Long menuItemId,
            @RequestParam Integer quantity,
            Model model) {
        Cart cart = cartService.addToCart(tableId, menuItemId, quantity);
        model.addAttribute("cart", cart);
        return "fragments/cart-sidebar :: cart-content";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long tableId,
            @RequestParam Long menuItemId,
            @RequestParam Integer quantity,
            Model model) {
        Cart cart = cartService.updateQuantity(tableId, menuItemId, quantity);
        model.addAttribute("cart", cart);
        return "fragments/cart-sidebar :: cart-content";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long tableId,
            @RequestParam Long menuItemId,
            Model model) {
        Cart cart = cartService.removeFromCart(tableId, menuItemId);
        model.addAttribute("cart", cart);
        return "fragments/cart-sidebar :: cart-content";
    }
}