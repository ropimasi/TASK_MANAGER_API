package dev.ropimasi.taskmanagerapi.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/taskmanager")
public class TaskManagerApiController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello! I'm Task Manager API.";
	}


	@GetMapping("/about")
	public String about() {
		String aboutInfo = """
				          Task Manager API is a RESTful API that allows users to manage their tasks.
				          It provides endpoints for creating, reading, updating, and deleting tasks.
				          The API is built using Java and Spring, following best practices for RESTful API design,
				          and clean-code principles.
				          """;
		return aboutInfo;
	}

}
