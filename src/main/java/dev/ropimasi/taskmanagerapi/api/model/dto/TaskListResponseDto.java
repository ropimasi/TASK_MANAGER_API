package dev.ropimasi.taskmanagerapi.api.model.dto;

import java.time.LocalDateTime;



public record TaskListResponseDto(Long id, String title, String description, Boolean completed,
		LocalDateTime createdAt) {
}
