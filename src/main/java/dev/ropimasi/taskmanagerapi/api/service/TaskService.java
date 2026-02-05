package dev.ropimasi.taskmanagerapi.api.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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
		/* There is an issue to be reviewed here: if the task is not found, an exception is thrown. Is this the desired
		 * behavior? If so, it's fine; otherwise, we might want to return an Optional<TaskRecoveringResponseDto> or
		 * handle it differently. Currently, it throws a ResponseStatusException with 404 status if not found. I don't
		 * like throwing HTTP specific exceptions in the service layer. //FURTHER: I could create a custom exception. 
		 * */
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + id));
		return taskMapper.toRecoveringResponseDto(task);

	}

}
