package com.example.foodordering.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long userId;
    private String customerName;
    private String email;
    private String address;
    private String paymentMethod = "CASH_ON_DELIVERY";
    @Enumerated(EnumType.STRING) private OrderStatus status = OrderStatus.PLACED;
    private double total;
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
    public CustomerOrder() {}
    public void addItem(OrderItem item){ items.add(item); item.setOrder(this); }
    public Long getId(){return id;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;}
    public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getAddress(){return address;} public void setAddress(String v){address=v;}
    public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;}
    public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus v){status=v;}
    public double getTotal(){return total;} public void setTotal(double v){total=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public List<OrderItem> getItems(){return items;}
}
