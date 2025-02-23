package MusinsaClone.order.DTO;

public record CreateOrderRequest(
        Long customerId,
        String address
) {
}
