package dev.ropimasi.taskmanagerapi.api.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.ropimasi.taskmanagerapi.api.core.TaskMapper;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;
import dev.ropimasi.taskmanagerapi.api.model.repository.TaskRepository;



@Service
public class TaskService {

	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;


	// Single constructor: Spring will automatically inject the dependencies.
	public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.taskMapper = taskMapper;
	}


	@Transactional
	public TaskCreatingResponseDto saveNewTask(TaskCreatingRequestDto taskDTO) {
		// Here we can add business logic before saving the task if needed.
		Task taskToSave = taskMapper.toEntity(taskDTO);
		Task savedTask = taskRepository.save(taskToSave);
		return taskMapper.toCreatingResponseDto(savedTask);
	}


	@Transactional(readOnly = true)
	public List<TaskRecoveringResponseDto> findAllTasks() {
		return taskRepository.findAll().stream().map(taskMapper::toRecoveringResponseDto).toList();
	}


	@Transactional(readOnly = true)
	public TaskRecoveringResponseDto findTaskById(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
		return taskMapper.toRecoveringResponseDto(task);

	}

}
