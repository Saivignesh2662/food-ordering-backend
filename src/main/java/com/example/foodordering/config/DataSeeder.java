package com.example.foodordering.config;
import com.example.foodordering.model.Food;
import com.example.foodordering.model.User;
import com.example.foodordering.repository.FoodRepository;
import com.example.foodordering.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataSeeder {
 @Bean CommandLineRunner seed(FoodRepository foods, UserRepository users, PasswordEncoder encoder) {
  return args -> {
   if(foods.count()==0){
    foods.save(new Food("Classic Veg Burger","Burgers","Crispy vegetable patty, lettuce and house sauce.",129,"https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=700",true));
    foods.save(new Food("Margherita Pizza","Pizza","Mozzarella, basil and rich tomato sauce.",249,"https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=700",true));
    foods.save(new Food("Paneer Tikka Wrap","Wraps","Spiced paneer, crunchy vegetables and mint sauce.",179,"https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=700",true));
    foods.save(new Food("Masala Fries","Sides","Golden fries tossed with Indian spices.",89,"https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=700",true));
    foods.save(new Food("Chocolate Brownie","Desserts","Warm chocolate brownie for a sweet finish.",99,"https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=700",true));
    foods.save(new Food("Cold Coffee","Drinks","Chilled creamy coffee.",119,"https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=700",true));
   }
   if(!users.existsByEmail("admin@foodie.local"))
    users.save(new User("Demo Admin","admin@foodie.local",encoder.encode("Admin@123"),"ADMIN"));
  };
 }
}
