package dev.ropimasi.taskmanager.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/taskmanager")
public class TaskManagerApiController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello, Task Manager API !";
	}


	@GetMapping("/about")
	public String about() {
		return "This is a simple Task Manager API built with Spring Boot.";
	}

}
