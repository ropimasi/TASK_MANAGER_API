package dev.ropimasi.taskmanagerapi.api.controller;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskPatchingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskPatchingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Tasks", description = "Management of livecycle of tasks: creation, retrieval, updating and deletion.")
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	private TaskService taskService;


	// Single constructor: Spring will automatically inject the dependencies.
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}


	@Operation(summary = "Create task", description = "Create a new task with the provided details. The request body"
			+ " must contain the necessary information for creating a task, such as title and description. If the task is successfully created, a 201 Created response is returned along with the details of the newly created task.")
	@PostMapping
	public ResponseEntity<TaskCreatingResponseDto> createTask(@RequestBody @Valid TaskCreatingRequestDto taskDTO) {
		TaskCreatingResponseDto savedTaskDto = taskService.saveNewTask(taskDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskDto);
	}


	@Operation(summary = "List all tasks", description = "Recovers a list of all tasks. The result is sorted by"
			+ " creation date in ascending order by default.")
	@GetMapping
	public ResponseEntity<List<TaskRecoveringResponseDto>> getAll(
			@Parameter(hidden = true) @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findAllTasks(sort);
		return ResponseEntity.ok(taskDtos);
	}


	@Operation(summary = "Select a task", description = "Recovers a existing task by its unique identifier. If the task"
			+ " is not found, a 404 Not Found response is returned.")
	@GetMapping("/{id}")
	public ResponseEntity<TaskRecoveringResponseDto> getById(@PathVariable Long id) {
		TaskRecoveringResponseDto taskDto = taskService.findTaskById(id);
		return ResponseEntity.ok(taskDto);
	}


	@Operation(summary = "List all completed tasks", description = "Recovers a list of all tasks whose completed status"
			+ " is true. The result is sorted by creation date in ascending order by default.")
	@GetMapping("/completed")
	public ResponseEntity<List<TaskRecoveringResponseDto>> getCompleted(
			@Parameter(hidden = true) @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findCompletedTasks(sort);
		return ResponseEntity.ok(taskDtos);
	}


	@Operation(summary = "List all not ompleted tasks", description = "Recovers a list of all tasks whose completed"
			+ " status is false. The result is sorted by creation date in ascending order by default.")
	@GetMapping("/not-completed")
	public ResponseEntity<List<TaskRecoveringResponseDto>> getNotCompleted(
			@Parameter(hidden = true) @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findNotCompletedTasks(sort);
		return ResponseEntity.ok(taskDtos);
	}

	
	@Operation(summary = "Search task by title", description = "Performs a case-insensitive search for tasks whose"
			+ " titles contain the specified keyword. The results can be sorted.")
	@GetMapping("/search")
	public ResponseEntity<List<TaskRecoveringResponseDto>> searchByTitle(
			@RequestParam String title,
			@Parameter(hidden = true) @SortDefault(sort = "createdAt", direction = Sort.Direction.ASC) Sort sort) {
		List<TaskRecoveringResponseDto> taskDtos = taskService.findTasksByTitleContainingIgnoreCase(title, sort);
		return ResponseEntity.ok(taskDtos);
	}


	@Operation(summary = "Update the whole task object", description = "Updates an existing task by replacing all its"
			+ " fields with the provided values. The request body must contain the complete details of the task to be"
			+ " updated, including title, description, and completed status. task If the")
	@PutMapping("/{id}")
	public ResponseEntity<TaskUpdatingResponseDto> updateTask(@PathVariable Long id,
			@RequestBody @Valid TaskUpdatingRequestDto taskDto) {

		TaskUpdatingResponseDto updatedTaskDto = taskService.updateTask(id, taskDto);
		return ResponseEntity.ok(updatedTaskDto);
	}

	
	@Operation(summary = "Update some fields of the task object", description = "Updates an existing task by modifying"
			+ " only the specified fields. body The request must contain the fields to be updated, such as title,"
			+ " description or completed status. Only the provided fields will be updated. If the task is successfully"
			+ " updated, a 200 OK response is returned along with the details of the updated task.")
	@PatchMapping("/{id}")
	public ResponseEntity<TaskPatchingResponseDto> patchTask(
	        @PathVariable Long id, 
	        @Valid @RequestBody TaskPatchingRequestDto patchDto) {
	    
	    TaskPatchingResponseDto responseDto = taskService.patchTask(id, patchDto);
	    return ResponseEntity.ok(responseDto);
	}
	

	@Operation(summary = "Delete a task", description = "Deletes an existing task by its unique identifier. If the task"
			+ " is successfully deleted, a 204 No Content response is returned. If the task is not found, a 404 Not Found"
			+ " response is returned.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return ResponseEntity.noContent().build();
	}
}
