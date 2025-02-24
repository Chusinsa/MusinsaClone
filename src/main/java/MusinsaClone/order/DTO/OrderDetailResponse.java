package MusinsaClone.order.DTO;

public record OrderDetailResponse(
        Long orderId,
        Long UserId,
        String address,
        int totalPrice
) {
}
