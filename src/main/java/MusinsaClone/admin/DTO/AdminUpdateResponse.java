package MusinsaClone.admin.dto;

import java.time.LocalDateTime;

public record AdminUpdateResponse(Long id, String nickName, String phoneNumber, LocalDateTime createdAt) {
}
