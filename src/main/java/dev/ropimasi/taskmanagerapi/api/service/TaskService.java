package dev.ropimasi.taskmanagerapi.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.ropimasi.taskmanagerapi.api.core.TaskMapper;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreateRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreateResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskListResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;
import dev.ropimasi.taskmanagerapi.api.model.repository.TaskRepository;



@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskMapper taskMapper;


	@Transactional
	public TaskCreateResponseDto saveNewTask(TaskCreateRequestDto taskDTO) {
		// Here we can add business logic before saving the task if needed.
		Task taskToSave = taskMapper.toEntity(taskDTO);
		Task savedTask = taskRepository.save(taskToSave);
		return taskMapper.toCreateResponseDto(savedTask);
	}


	@Transactional(readOnly = true)
	public List<TaskListResponseDto> findAllTasks() {
		return taskRepository.findAll().stream().map(taskMapper::toListResponseDto).toList();
	}

}
