package MusinsaClone.product.dto;

import MusinsaClone.product.Condition;

import java.util.List;

public record ProductUpdateRequest(
        String productName,
        int price,
        String description,
        Condition productCondition,
        List<ProductOptionRequest> productOptions
) {
}
