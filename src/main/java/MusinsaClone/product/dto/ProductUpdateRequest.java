package MusinsaClone.product.dto;

import MusinsaClone.product.Category;
import MusinsaClone.product.Condition;

import java.time.LocalDateTime;
import java.util.List;

public record ProductUpdateRequest(
        String productName,
        int price,
        String description,
        Condition productCondition,
        List<OptionGroup> options
) {
}
