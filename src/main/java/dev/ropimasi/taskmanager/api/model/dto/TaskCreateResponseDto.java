package dev.ropimasi.taskmanager.api.model.dto;

public record TaskCreateResponseDto(Long id, String title, String description, Boolean completed) {
}
