package dev.ropimasi.taskmanagerapi.api.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.ropimasi.taskmanagerapi.api.core.TaskMapper;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;
import dev.ropimasi.taskmanagerapi.api.model.repository.TaskRepository;
import dev.ropimasi.taskmanagerapi.api.service.exception.ResourceNotFoundException;



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
	public List<TaskRecoveringResponseDto> findAllTasks(Sort sort) {
		return taskRepository.findAll(sort).stream().map(taskMapper::toRecoveringResponseDto).toList();
	}


	@Transactional(readOnly = true)
	public List<TaskRecoveringResponseDto> findCompletedTasks() {
		return taskRepository.findByCompletedTrue().stream().map(taskMapper::toRecoveringResponseDto).toList();
	}


	@Transactional(readOnly = true)
	public List<TaskRecoveringResponseDto> findNotCompletedTasks() {
		return taskRepository.findByCompletedFalse().stream().map(taskMapper::toRecoveringResponseDto).toList();
	}


	@Transactional(readOnly = true)
	public TaskRecoveringResponseDto findTaskById(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
		return taskMapper.toRecoveringResponseDto(task);
	}


	@Transactional(readOnly = true)
	public List<TaskRecoveringResponseDto> findTasksByTitleContainingIgnoreCase(String title, Sort sort) {
		return taskRepository.findByTitleContainingIgnoreCase(title, sort)
				.stream()
				.map(taskMapper::toRecoveringResponseDto)
				.toList();
	}


	@Transactional
	public TaskUpdatingResponseDto updateTask(Long id, TaskUpdatingRequestDto taskDto) {
		Task existingTask = taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

		taskMapper.updateEntityFromDto(existingTask, taskDto);

		/* Since we are in a @Transactional method, the changes to existingTask will be automatically
		 * detected (by Hibernate) and persisted when the transaction commits. So I let the line below
		 * commented out, but it is not strictly necessary to call save() here. Performance optimization.
		 * */
		// taskRepository.save(existingTask);
		return taskMapper.toUpdatingResponseDto(existingTask);
	}


	@Transactional
	public void deleteTask(Long id) {
		if (!taskRepository.existsById(id)) {
			throw new ResourceNotFoundException("Cannot delete. Task not found with id: " + id);
		}

		taskRepository.deleteById(id);
	}

}

//
//
//
//
//
//