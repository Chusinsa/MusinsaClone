package MusinsaClone.product.dto;

import MusinsaClone.product.Condition;

import java.time.LocalDateTime;
import java.util.List;

public record ProductUpdateResponse(
        Long id,
        String productName,
        int price,
        String description,
        Condition productCondition,
        List<ProductOptionResponse> productOption,
        LocalDateTime updateAt
) {
}