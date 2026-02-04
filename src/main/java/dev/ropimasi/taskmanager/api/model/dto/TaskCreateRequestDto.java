package dev.ropimasi.taskmanager.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public record TaskCreateRequestDto(
		@NotBlank(message = "Title is required")
		@Size(min = 1, max = 128, message = "Title must be between 1 and 128 characters")
		String title,
		String description) {

	/*public TaskCreateRequestDto(String title, String description) {
		this.title = title;
		this.description = description;
	}*/
}
