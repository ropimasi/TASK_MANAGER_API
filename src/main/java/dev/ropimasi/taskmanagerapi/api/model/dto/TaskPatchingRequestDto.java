package dev.ropimasi.taskmanagerapi.api.model.dto;

import jakarta.validation.constraints.Size;



public record TaskPatchingRequestDto(
		@Size(min = 1, max = 128, message = "Title must have between 1 and 128 characters")
		String title,
		String description,
		Boolean completed) {
}
