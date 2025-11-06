package com.ecommerce.order.controller;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

   
    private  OrderService orderService;
    private  OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository,OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }
   // Liste des commandes
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "orders/orders";
    }

    // Formulaire d’ajout
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/add-order";
    }

    // Sauvegarde du formulaire
    @PostMapping
    public String addOrder(@ModelAttribute Order order) {
        orderRepository.save(order);
        return "redirect:/orders";
    }

    
    // Supprimer une commande
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

// @PostMapping
// public Order createOrder(
//         @RequestParam String orderId,
//         @RequestParam String customerId,
//         @RequestParam String productId,
//         @RequestParam int quantity,
//         @RequestParam double price,
//         @RequestParam String orderDate
// ) {
//     Order order = Order.builder()
//             .orderId(orderId)
//             .customerId(customerId)
//             .productId(productId)
//             .quantity(quantity)
//             .price(price)
//             .orderDate(orderDate)
//             .build();

//     return orderService.createOrder(order);
// }

}
