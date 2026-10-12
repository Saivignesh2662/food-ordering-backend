package com.example.foodordering.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
public class Food {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    @NotBlank private String category;
    @NotBlank @Column(length=1000) private String description;
    @PositiveOrZero private double price;
    private String imageUrl;
    private boolean available = true;
    public Food() {}
    public Food(String name,String category,String description,double price,String imageUrl,boolean available){
        this.name=name;this.category=category;this.description=description;this.price=price;this.imageUrl=imageUrl;this.available=available;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public double getPrice(){return price;} public void setPrice(double v){price=v;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
}
