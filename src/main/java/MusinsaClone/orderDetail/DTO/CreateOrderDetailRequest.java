package MusinsaClone.orderDetail.DTO;

public record CreateOrderDetailRequest(
        Long orderId,
        Long productId,
        int productCount,
        int price
) {
}
