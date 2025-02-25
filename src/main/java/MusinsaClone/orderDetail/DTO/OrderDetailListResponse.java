package MusinsaClone.orderDetail.DTO;

import java.util.List;

public record OrderDetailListResponse(
        Long orderId,
        List<OrderDetailResponse> orderDetailResponseList
) {
}
