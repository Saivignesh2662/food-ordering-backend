package com.example.foodordering.controller;
import com.example.foodordering.model.Food;
import com.example.foodordering.repository.FoodRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/foods")
public class FoodController {
    private final FoodRepository foods;
    public FoodController(FoodRepository foods){this.foods=foods;}
    @GetMapping public Object all(){return foods.findAll();}
    @GetMapping("/available") public Object available(){return foods.findByAvailableTrue();}
    @PostMapping public ResponseEntity<?> create(@Valid @RequestBody Food food,HttpSession s){
        if(!"ADMIN".equals(s.getAttribute("userRole"))) return ResponseEntity.status(403).body(Map.of("message","Admin access required"));
        food.setId(null); return ResponseEntity.status(201).body(foods.save(food));
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody Food input,HttpSession s){
        if(!"ADMIN".equals(s.getAttribute("userRole"))) return ResponseEntity.status(403).body(Map.of("message","Admin access required"));
        return foods.findById(id).map(f->{f.setName(input.getName());f.setCategory(input.getCategory());f.setDescription(input.getDescription());f.setPrice(input.getPrice());f.setImageUrl(input.getImageUrl());f.setAvailable(input.isAvailable());return ResponseEntity.ok(foods.save(f));})
        .orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable Long id,HttpSession s){
        if(!"ADMIN".equals(s.getAttribute("userRole"))) return ResponseEntity.status(403).body(Map.of("message","Admin access required"));
        if(!foods.existsById(id)) return ResponseEntity.notFound().build(); foods.deleteById(id);return ResponseEntity.noContent().build();
    }
}
