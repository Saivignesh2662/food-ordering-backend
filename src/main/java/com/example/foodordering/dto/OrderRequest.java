package com.example.foodordering.dto;
import jakarta.validation.constraints.*;
import java.util.List;
public record OrderRequest(@NotBlank String customerName, @Email @NotBlank String email, @NotBlank String address,
                           List<Line> items, String paymentMethod) {
    public record Line(@NotNull Long foodId, @Min(1) int quantity) {}
}
