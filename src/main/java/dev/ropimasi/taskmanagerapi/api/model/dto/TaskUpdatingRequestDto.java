package dev.ropimasi.taskmanagerapi.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public record TaskUpdatingRequestDto(
		@NotBlank(message = "Title is required")
		@Size(min = 1, max = 128, message = "Title must be between 1 and 128 characters")
		String title,
		String description,
        @NotNull(message = "Completed status is required")
		Boolean completed) {
}
