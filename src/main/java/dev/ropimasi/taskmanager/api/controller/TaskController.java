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
import dev.ropimasi.taskmanager.api.dto.TaskCreateRequestDTO;
import dev.ropimasi.taskmanager.api.entity.Task;
import dev.ropimasi.taskmanager.api.repository.TaskRepository;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;


	@GetMapping
	public ResponseEntity<List<Task>> listAll() {
		List<Task> tasks = taskRepository.findAll();
		return ResponseEntity.ok(tasks);
	}


	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody @Valid TaskCreateRequestDTO taskDTO) {

		Task taskToSave = new Task();
		taskToSave.setTitle(taskDTO.title());
		taskToSave.setDescription(taskDTO.description());

		Task savedTask = taskRepository.save(taskToSave);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
	}

}
