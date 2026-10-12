package com.example.foodordering.controller;
import com.example.foodordering.dto.OrderRequest;
import com.example.foodordering.model.*;
import com.example.foodordering.repository.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orders; private final FoodRepository foods;
    public OrderController(OrderRepository orders,FoodRepository foods){this.orders=orders;this.foods=foods;}
    @PostMapping public ResponseEntity<?> place(@Valid @RequestBody OrderRequest request,HttpSession session){
        Object userId=session.getAttribute("userId");
        if(userId==null) return ResponseEntity.status(401).body(Map.of("message","Please login before placing an order"));
        if(request.items()==null || request.items().isEmpty()) return ResponseEntity.badRequest().body(Map.of("message","Your cart is empty"));
        CustomerOrder order=new CustomerOrder(); order.setUserId((Long)userId); order.setCustomerName(request.customerName());
        order.setEmail(request.email()); order.setAddress(request.address());
        order.setPaymentMethod(request.paymentMethod()==null?"CASH_ON_DELIVERY":request.paymentMethod());
        double total=0;
        for(OrderRequest.Line line:request.items()){
            if(line.quantity()<1) return ResponseEntity.badRequest().body(Map.of("message","Quantity must be at least 1"));
            Food food=foods.findById(line.foodId()).orElse(null);
            if(food==null || !food.isAvailable()) return ResponseEntity.badRequest().body(Map.of("message","A selected food item is unavailable"));
            order.addItem(new OrderItem(food.getId(),food.getName(),food.getPrice(),line.quantity()));
            total += food.getPrice()*line.quantity();
        }
        order.setTotal(Math.round(total*100.0)/100.0);
        return ResponseEntity.status(201).body(orders.save(order));
    }
    @GetMapping("/my") public ResponseEntity<?> mine(HttpSession session){
        Object id=session.getAttribute("userId"); if(id==null)return ResponseEntity.status(401).body(Map.of("message","Please login"));
        return ResponseEntity.ok(orders.findByUserIdOrderByCreatedAtDesc((Long)id));
    }
    @GetMapping public ResponseEntity<?> all(HttpSession session){
        if(!"ADMIN".equals(session.getAttribute("userRole")))return ResponseEntity.status(403).body(Map.of("message","Admin access required"));
        return ResponseEntity.ok(orders.findAll());
    }
    @PatchMapping("/{id}/status") public ResponseEntity<?> status(@PathVariable Long id,@RequestParam OrderStatus status,HttpSession session){
        if(!"ADMIN".equals(session.getAttribute("userRole")))return ResponseEntity.status(403).body(Map.of("message","Admin access required"));
        return orders.findById(id).map(o->{o.setStatus(status);return ResponseEntity.ok(orders.save(o));}).orElseGet(()->ResponseEntity.notFound().build());
    }
}
