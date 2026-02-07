package dev.ropimasi.taskmanagerapi.api.model.dto;

public record TaskCreatingResponseDto(
		Long id,
		String title,
		String description,
		Boolean completed) {
}
