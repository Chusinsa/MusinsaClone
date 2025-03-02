package MusinsaClone.product.dto;

public record ProductOptionResponse(
        Long optionId,
        String color,
        String size,
        int stock
) {
}
