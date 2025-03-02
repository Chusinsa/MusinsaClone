package MusinsaClone.product.dto;

import MusinsaClone.product.Category;
import MusinsaClone.product.Condition;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long productId,
        String productName,
        int price,
        String description,
        Category category,
        Condition productCondition,
        List<ProductOptionResponse> productOption,
        LocalDateTime createAt
) {
}
