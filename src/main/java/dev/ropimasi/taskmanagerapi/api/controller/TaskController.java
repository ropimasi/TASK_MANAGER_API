package dev.ropimasi.taskmanagerapi.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingResponseDto;
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
	public ResponseEntity<List<TaskRecoveringResponseDto>> getAll(
			@SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findAllTasks(sort);
		return ResponseEntity.ok(taskDtos);
	}


	@GetMapping("/{id}")
	public ResponseEntity<TaskRecoveringResponseDto> getById(@PathVariable Long id) {
		TaskRecoveringResponseDto taskDto = taskService.findTaskById(id);
		return ResponseEntity.ok(taskDto);
	}


	@GetMapping("/completed")
	public ResponseEntity<List<TaskRecoveringResponseDto>> getCompleted() {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findCompletedTasks();
		return ResponseEntity.ok(taskDtos);
	}


	@GetMapping("/not-completed")
	public ResponseEntity<List<TaskRecoveringResponseDto>> getNotCompleted() {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findNotCompletedTasks();
		return ResponseEntity.ok(taskDtos);
	}


	@GetMapping("/search")
	public ResponseEntity<List<TaskRecoveringResponseDto>> searchByTitle(
			@RequestParam String title,
			@SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findTasksByTitleContainingIgnoreCase(title, sort);
		return ResponseEntity.ok(taskDtos);

	}


	@PutMapping("/{id}")
	public ResponseEntity<TaskUpdatingResponseDto> updateTask(@PathVariable Long id,
			@RequestBody @Valid TaskUpdatingRequestDto taskDto) {

		TaskUpdatingResponseDto updatedTaskDto = taskService.updateTask(id, taskDto);
		return ResponseEntity.ok(updatedTaskDto);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
