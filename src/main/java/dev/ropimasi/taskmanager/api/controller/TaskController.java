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
import dev.ropimasi.taskmanager.api.model.dto.TaskListResponseDto;
import dev.ropimasi.taskmanager.api.model.repository.TaskRepository;
import dev.ropimasi.taskmanager.api.service.TaskService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;


	@PostMapping
	public ResponseEntity<TaskCreateResponseDto> createTask(@RequestBody @Valid TaskCreateRequestDto taskDTO) {
		TaskCreateResponseDto savedTaskDto = taskService.saveNewTask(taskDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskDto);
	}


	@GetMapping
	public ResponseEntity<List<TaskListResponseDto>> listAll() {
		List<TaskListResponseDto> tasks = taskService.findAllTasks();
		return ResponseEntity.ok(tasks);
	}

}
