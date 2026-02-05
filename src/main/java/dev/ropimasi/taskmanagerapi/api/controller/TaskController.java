package dev.ropimasi.taskmanagerapi.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.service.TaskService;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;


	@PostMapping
	public ResponseEntity<TaskCreatingResponseDto> createTask(@RequestBody @Valid TaskCreatingRequestDto taskDTO) {
		TaskCreatingResponseDto savedTaskDto = taskService.saveNewTask(taskDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskDto);
	}


	@GetMapping
	public ResponseEntity<List<TaskRecoveringResponseDto>> listAll() {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findAllTasks();
		return ResponseEntity.ok(taskDtos);
	}


	@GetMapping("/{id}")
	public ResponseEntity<TaskRecoveringResponseDto> getById(@PathVariable Long id) {
		TaskRecoveringResponseDto taskDto = taskService.findTaskById(id);
		return ResponseEntity.ok(taskDto);
	}

}
