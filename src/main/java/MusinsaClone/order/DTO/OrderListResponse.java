package MusinsaClone.order.DTO;

import java.util.List;

public record OrderListResponse(
        List<OrderInfo> orders
) {
    public record OrderInfo(
            Long orderId,
            String customerName
    ) {
    }
}
