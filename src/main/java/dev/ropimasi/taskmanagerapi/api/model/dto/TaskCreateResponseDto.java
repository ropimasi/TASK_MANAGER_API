package dev.ropimasi.taskmanagerapi.api.model.dto;

public record TaskCreateResponseDto(Long id, String title, String description, Boolean completed) {
}
