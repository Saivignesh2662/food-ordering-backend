package com.example.foodordering.repository;
import com.example.foodordering.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> { List<CustomerOrder> findByUserIdOrderByCreatedAtDesc(Long userId); }
