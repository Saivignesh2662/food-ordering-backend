package com.example.foodordering.controller;
import com.example.foodordering.dto.*;
import com.example.foodordering.model.User;
import com.example.foodordering.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users; private final PasswordEncoder encoder;
    public AuthController(UserRepository users, PasswordEncoder encoder){this.users=users;this.encoder=encoder;}

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, HttpSession session) {
        if(users.existsByEmail(request.email().toLowerCase())) return ResponseEntity.badRequest().body(Map.of("message","Email is already registered"));
        User user=new User(request.name().trim(),request.email().toLowerCase(),encoder.encode(request.password()),"USER");
        users.save(user); session.setAttribute("userId",user.getId()); session.setAttribute("userRole",user.getRole());
        return ResponseEntity.ok(Map.of("id",user.getId(),"name",user.getName(),"email",user.getEmail(),"role",user.getRole()));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user=users.findByEmail(request.email().toLowerCase()).orElse(null);
        if(user==null || !encoder.matches(request.password(),user.getPassword())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid email or password"));
        session.setAttribute("userId",user.getId()); session.setAttribute("userRole",user.getRole());
        return ResponseEntity.ok(Map.of("id",user.getId(),"name",user.getName(),"email",user.getEmail(),"role",user.getRole()));
    }
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Object id=session.getAttribute("userId"); if(id==null) return ResponseEntity.status(401).body(Map.of("message","Not logged in"));
        return users.findById((Long)id).map(u->ResponseEntity.ok(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole())))
            .orElseGet(()->ResponseEntity.status(401).body(Map.of("message","Not logged in")));
    }
    @PostMapping("/logout") public Map<String,String> logout(HttpSession session){session.invalidate();return Map.of("message","Logged out");}
}
