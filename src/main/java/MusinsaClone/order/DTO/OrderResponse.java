package MusinsaClone.order.DTO;

import java.time.LocalDateTime;

public record OrderResponse(
        Long customerId,
        String address,
        LocalDateTime createdAt
) {
}
