package MusinsaClone.product.dto;

import MusinsaClone.product.Category;
import MusinsaClone.product.Condition;

public record ProductListResponse(
        Long productId,
        String productName,
        int price,
        Category category,
        Condition productCondition
) {
}
