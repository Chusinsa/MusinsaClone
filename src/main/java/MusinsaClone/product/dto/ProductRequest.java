package MusinsaClone.product.dto;

import MusinsaClone.product.Category;
import MusinsaClone.product.Condition;

import java.util.List;

public record ProductRequest(
        String productName,
        int price,
        String description,
        Category category,
        Condition productCondition,
        List<ProductOptionRequest> productOption
) {
}
