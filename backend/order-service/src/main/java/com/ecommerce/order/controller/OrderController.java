package com.ecommerce.order.controller;

import com.ecommerce.order.model.Order;
import com.ecommerce.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // Injection via constructeur
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Liste des commandes
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll()); // nécessite findAll() dans le service (voir note)
        return "orders/orders";
    }

    // Formulaire d’ajout
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/add-order";
    }

    // Sauvegarde du formulaire -> utilise le service pour DB + Kafka
    @PostMapping
    public String addOrder(@ModelAttribute("order") @Valid Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/add-order";
        }
        orderService.createOrder(order); // IMPORTANT : sauvegarde + envoi Kafka
        return "redirect:/orders";
    }

    // Supprimer une commande
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id); // recommande géré par le service
        return "redirect:/orders";
    }
}
