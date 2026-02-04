package dev.ropimasi.taskmanager.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.ropimasi.taskmanager.api.model.dto.TaskCreateRequestDto;
import dev.ropimasi.taskmanager.api.model.dto.TaskCreateResponseDto;
import dev.ropimasi.taskmanager.api.model.entity.Task;
import dev.ropimasi.taskmanager.api.model.repository.TaskRepository;
import dev.ropimasi.taskmanager.api.service.TaskService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;


	@GetMapping
	public ResponseEntity<List<Task>> listAll() {
		List<Task> tasks = taskRepository.findAll();
		return ResponseEntity.ok(tasks);
	}


	@PostMapping
	public ResponseEntity<TaskCreateResponseDto> createTask(@RequestBody @Valid TaskCreateRequestDto taskDTO) {
		TaskCreateResponseDto savedTaskDto = taskService.saveNewTask(taskDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskDto);
	}

}
