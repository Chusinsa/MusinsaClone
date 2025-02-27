package MusinsaClone.product.dto;

import MusinsaClone.product.Category;
import MusinsaClone.product.Condition;

import java.time.LocalDateTime;
import java.util.List;

public record ProductCreateResponse(
        Long id,
        String productName,
        int price,
        String description,
        Category category,
        Condition productCondition,
        List<OptionGroup> options,
        LocalDateTime createAt
) {
}
