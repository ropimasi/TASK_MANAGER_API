package dev.ropimasi.taskmanagerapi.api.core;

import org.springframework.stereotype.Component;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreateRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreateResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskListResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;



@Component
public class TaskMapper {
	public Task toEntity(TaskCreateRequestDto dto) {
		Task task = new Task();
		task.setTitle(dto.title());
		task.setDescription(dto.description());
		return task;
	}


	public TaskCreateRequestDto toCreateRequestDto(Task task) {
		return new TaskCreateRequestDto(task.getTitle(), task.getDescription());
	}


	public TaskCreateResponseDto toCreateResponseDto(Task task) {
		return new TaskCreateResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
	}


	public TaskListResponseDto toListResponseDto(Task task) {
		return new TaskListResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted(),
				task.getCreatedAt());
	}

}
