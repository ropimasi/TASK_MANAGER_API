package dev.ropimasi.taskmanagerapi.api.core;

import org.springframework.stereotype.Component;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;



@Component
public class TaskMapper {
	public Task toEntity(TaskCreatingRequestDto dto) {
		Task task = new Task();
		task.setTitle(dto.title());
		task.setDescription(dto.description());
		task.setCompleted(false); // New tasks are not completed by default.
		return task;
	}


	public TaskCreatingRequestDto toCreatingRequestDto(Task task) {
		return new TaskCreatingRequestDto(task.getTitle(), task.getDescription());
	}


	public TaskCreatingResponseDto toCreatingResponseDto(Task task) {
		return new TaskCreatingResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
	}


	public TaskRecoveringResponseDto toRecoveringResponseDto(Task task) {
		return new TaskRecoveringResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted(),
				task.getCreatedAt());
	}


	public TaskUpdatingResponseDto toUpdatingResponseDto(Task task) {
		return new TaskUpdatingResponseDto(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted(),
				task.getUpdatedAt());
	}


	public void updateEntityFromDto(Task task, TaskUpdatingRequestDto taskDto) {
		/* We have reflexion here: There is a approach would do verification weather the fields are null or not.
		 * In this case we would only update all fields that are null and not null in the DTO, bacause it could
		 * wished as null.
		 * */
		task.setTitle(taskDto.title());
		task.setDescription(taskDto.description());
		task.setCompleted(taskDto.completed());
	}

}
