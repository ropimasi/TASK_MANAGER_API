package dev.ropimasi.taskmanager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.ropimasi.taskmanager.api.core.TaskMapper;
import dev.ropimasi.taskmanager.api.model.dto.TaskCreateRequestDto;
import dev.ropimasi.taskmanager.api.model.dto.TaskCreateResponseDto;
import dev.ropimasi.taskmanager.api.model.entity.Task;
import dev.ropimasi.taskmanager.api.model.repository.TaskRepository;



@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskMapper taskMapper;


	public TaskCreateResponseDto saveNewTask(TaskCreateRequestDto taskDTO) {
		// Here we can add business logic before saving the task if needed.
		Task taskToSave = taskMapper.toEntity(taskDTO);
		Task savedTask = taskRepository.save(taskToSave);
		return taskMapper.toCreateResponseDto(savedTask);
	}

}
