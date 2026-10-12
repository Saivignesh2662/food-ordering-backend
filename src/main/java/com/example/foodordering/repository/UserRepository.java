package com.example.foodordering.repository;
import com.example.foodordering.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,Long> { Optional<User> findByEmail(String email); boolean existsByEmail(String email); }
