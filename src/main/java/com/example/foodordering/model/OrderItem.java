package com.example.foodordering.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
@Entity
public class OrderItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long foodId;
    private String foodName;
    private double unitPrice;
    private int quantity;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id")
    @JsonIgnore private CustomerOrder order;
    public OrderItem() {}
    public OrderItem(Long foodId,String foodName,double unitPrice,int quantity){this.foodId=foodId;this.foodName=foodName;this.unitPrice=unitPrice;this.quantity=quantity;}
    public Long getId(){return id;} public Long getFoodId(){return foodId;} public String getFoodName(){return foodName;}
    public double getUnitPrice(){return unitPrice;} public int getQuantity(){return quantity;}
    public CustomerOrder getOrder(){return order;} public void setOrder(CustomerOrder v){order=v;}
}
