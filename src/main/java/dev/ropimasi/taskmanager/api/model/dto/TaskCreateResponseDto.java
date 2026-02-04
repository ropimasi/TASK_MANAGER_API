package dev.ropimasi.taskmanager.api.model.dto;

public record TaskCreateResponseDto(Long id, String title, String description, Boolean completed) {

	/*public TaskCreateResponseDto(Long id, String title, String description, Boolean completed) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}*/

}
