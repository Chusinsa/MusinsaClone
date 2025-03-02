package MusinsaClone.orderDetail.DTO;

public record OrderDetailviewResponse(
        Long orderDetailId,
        Long orderId,
        Long productId,
        int productCount,
        int price
) {
}
