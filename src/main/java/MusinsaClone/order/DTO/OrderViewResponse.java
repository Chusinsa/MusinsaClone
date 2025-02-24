package MusinsaClone.order.DTO;

public record OrderViewResponse(
        Long orderId,
        Long customerId,
        String address,
        int totalPrice
) {
}
